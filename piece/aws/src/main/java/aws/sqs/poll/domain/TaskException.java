package aws.sqs.poll.domain;

public class TaskException extends RuntimeException {

	private static final long serialVersionUID = -1561092125312952815L;

	public TaskException(String msg, Exception ex) {
		super(msg, ex);
	}
}
