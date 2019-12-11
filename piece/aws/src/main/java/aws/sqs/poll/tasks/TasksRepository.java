package aws.sqs.poll.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import aws.sqs.poll.domain.AbstractTask;
import aws.sqs.poll.domain.DefaultTask;
import aws.sqs.poll.domain.TaskDescription;
import aws.sqs.poll.domain.TaskException;
import aws.sqs.poll.domain.TaskFactory;

public class TasksRepository implements TaskFactory {
	private Map<String, Class<? extends AbstractTask>> name2Task;
	
	public void register(String name, Class<? extends AbstractTask> clazz) {
		name2Task.put(name, clazz);
	}
	
	public TasksRepository(String taskPackage) {
		name2Task = new HashMap<>();
		listClasses(taskPackage);
	}
	
	protected void listClasses(String taskPackage) {
		Reflections reflections = new Reflections(taskPackage);
		Set<Class<? extends AbstractTask>> tasks = reflections.getSubTypesOf(AbstractTask.class);
		
		for (Class<? extends AbstractTask> clazz : tasks) {
			String simpleName = clazz.getSimpleName();
			
			if (StringUtils.isEmpty(simpleName)) {
				continue;
			}
			register(simpleName.replace("Task", "").toLowerCase(), clazz);
			System.out.println("add task: " + simpleName.replace("Task", "").toLowerCase());
		}
	}

	@Override
	public AbstractTask createTask(TaskDescription msg) {
		Class<? extends AbstractTask> clazz = name2Task.getOrDefault(msg.getTask().toLowerCase(), DefaultTask.class);
		try {
			AbstractTask newInstance = clazz.newInstance();
			newInstance.setDesc(msg);
			return newInstance;
		} catch (InstantiationException|IllegalAccessException e) {
			throw new TaskException("create task failed", e);
		}
	}
	
	
}
