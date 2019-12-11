package aws.sqs.test;

import java.util.List;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import aws.sqs.poll.domain.AbstractTask;
import aws.sqs.poll.domain.TaskDescription;
import aws.sqs.poll.domain.TaskFactory;

public class SQSProcesser {
	private final AmazonSQS sqs;
	private final String queueUrl;
	private ObjectMapper mapper = new ObjectMapper();
	private TaskFactory taskFactory;
	private volatile boolean run = true;

	public SQSProcesser(String queueUrl, TaskFactory taskFactory) {
		sqs = AmazonSQSClientBuilder.defaultClient();
		this.queueUrl = queueUrl;
		this.taskFactory = taskFactory;
	}

	public void start() {
		try {
			while (run) {
				ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl);
				request.setVisibilityTimeout(3600);
				ReceiveMessageResult receiveMessage = sqs.receiveMessage(request);
				List<Message> messages = receiveMessage.getMessages();
				System.out.println("receive messages : " + messages.size());
				for (Message msg : messages) {
					safeHandle(msg);
				}
				if (run) {
					Thread.sleep(5000);
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	public void stop() {
		run = false;
	}

	private void safeHandle(Message msg) {
		try {
			TaskDescription description = mapper.readValue(msg.getBody(), TaskDescription.class);
			AbstractTask task = taskFactory.createTask(description);
			new SqsMessageCleaner(task, msg.getReceiptHandle()).run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	class SqsMessageCleaner implements Runnable {
		private AbstractTask runnable;
		private String receiptHandle;

		public SqsMessageCleaner(AbstractTask runnable, String receiptHandle) {
			this.runnable = runnable;
			this.receiptHandle = receiptHandle;
		}

		@Override
		public void run() {
			long start = System.currentTimeMillis();
			try {
				runnable.run();
			} finally {
				long runTime = System.currentTimeMillis() - start;
				System.out.println(
						"run task [" + runnable.getDesc().getTask() + "] in :" + runTime + ", " + runnable.isSuccess());
				if (runnable.isSuccess()) {
					sqs.deleteMessage(queueUrl, receiptHandle);
				} else {
					sqs.changeMessageVisibility(queueUrl, receiptHandle, 60 + (int)(runTime / 1000));
				}
			}
		}

	}
}
