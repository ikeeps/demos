package aws.sqs.poll.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class TaskDescription {
	private String task;
	private Map<String, String> options = new HashMap<>();

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	@JsonAnySetter
	public void add(String key, String value) {
		options.put(key, value);
	}

	public Map<String, String> getOptions() {
		return options;
	}
	
	
}
