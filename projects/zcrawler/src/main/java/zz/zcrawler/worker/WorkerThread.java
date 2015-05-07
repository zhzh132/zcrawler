package zz.zcrawler.worker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zz.zcrawler.data.ConfigStorage;
import zz.zcrawler.data.URLStorage;
import zz.zcrawler.task.Task;
import zz.zcrawler.task.TaskManager;
import zz.zcrawler.url.WebURL;

public class WorkerThread extends Thread implements Worker {

	private static Log log = LogFactory.getLog(WorkerThread.class);
	
	private byte status = Worker.WAITING;
	private TaskManager taskManager;
	private URLStorage urlStorage;
	private ConfigStorage configStorage;
	
	@Override
	public boolean isWaitingOrDead() {
		return status == Worker.WAITING || status == Worker.DEAD;
	}
	
	@Override
	public void run() {
		log.debug(this.getName() + " started.");
		
		while (true) {
			this.status = Worker.WAITING;
			Task task = taskManager.getTask(this.getName());
			if (task != null) {
				log.debug("Got task " + task.toString());
				if(task.getType().equals(Task.STOP)) {
					break;
				}
				this.status = Worker.WORKING;
				doTask(task);
			} else {
				// wait 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		this.status = Worker.DEAD;
		log.debug(this.getName() + " stopped.");
	}
	
	private void doTask(Task task) {
		for(WebURL url : task.getUrls()) {
			System.out.println(this.getName() + ": " + url);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public URLStorage getUrlStorage() {
		return urlStorage;
	}

	public void setUrlStorage(URLStorage urlStorage) {
		this.urlStorage = urlStorage;
	}

	public ConfigStorage getConfigStorage() {
		return configStorage;
	}

	public void setConfigStorage(ConfigStorage configStorage) {
		this.configStorage = configStorage;
	}

	@Override
	public byte getStatus() {
		return this.status;
	}

}
