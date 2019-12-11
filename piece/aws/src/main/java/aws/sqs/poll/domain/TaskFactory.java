package aws.sqs.poll.domain;

public interface TaskFactory {
	AbstractTask createTask(TaskDescription msg);
}
