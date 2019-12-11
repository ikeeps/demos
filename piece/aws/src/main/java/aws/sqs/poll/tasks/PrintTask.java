package aws.sqs.poll.tasks;

import aws.sqs.poll.domain.AbstractTask;

public class PrintTask extends AbstractTask {

	@Override
	public void run() {
		System.out.println(desc.getTask());
		System.out.println(desc.getClass());
		System.out.println(desc.getOptions());
	}
	
}
