package aws.sqs.poll.domain;

public abstract class AbstractTask implements Runnable {
	protected TaskDescription desc;
	protected volatile boolean success = true;

	public AbstractTask() {
	}

	public TaskDescription getDesc() {
		return desc;
	}

	public void setDesc(TaskDescription desc) {
		this.desc = desc;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
