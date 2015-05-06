package zz.zcrawler.task;

import java.util.List;

import zz.zcrawler.data.URLStorage;
import zz.zcrawler.url.WebURL;

public class TaskManager {

	private int taskSize;
	private URLStorage urlStorage;
	private boolean stopped = false;

	public TaskManager() {
	}
	
	
	// return null if there is no task
	public synchronized Task getTask() {
		if(stopped) {
			Task t = new Task();
			t.setType(Task.STOP);
			return t;
		}
		
		List<WebURL> urlList = urlStorage.get(taskSize);
		if(urlList.isEmpty()) {
			return null;
		}
		Task t = new Task();
		t.setUrls(urlList);
		return t;
	}
	
	public void stop() {
		this.stopped = true;
	}
	

	public int getTaskSize() {
		return taskSize;
	}


	public void setTaskSize(int taskSize) {
		this.taskSize = taskSize;
	}


	public URLStorage getUrlStorage() {
		return urlStorage;
	}


	public void setUrlStorage(URLStorage urlStorage) {
		this.urlStorage = urlStorage;
	}


}
