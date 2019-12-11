package aws.sqs.test;

import aws.sqs.poll.tasks.TasksRepository;

public class Dispatcher {

	public static void main(String[] args) {
		final String sqsUrl = args[0];
		System.out.println("listen on: " + sqsUrl);
		final SQSProcesser subcriber = new SQSProcesser(sqsUrl, new TasksRepository("aws.sqs.poll.tasks"));

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				subcriber.stop();
			}
		}));
		
		subcriber.start();
		

	}
}
