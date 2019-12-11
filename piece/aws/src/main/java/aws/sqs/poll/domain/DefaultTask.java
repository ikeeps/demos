package aws.sqs.poll.domain;

public class DefaultTask extends AbstractTask {

	@Override
	public void run() {
		System.out.println("no task found: " + desc.getTask());
	}

}
