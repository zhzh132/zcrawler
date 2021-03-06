package zz.zcrawler.task;

import java.util.List;

import zz.zcrawler.StorageFacade;
import zz.zcrawler.url.WebURL;

public class TaskManager {

	private int taskSize = 10;
	private boolean stopped = false;
	private StorageFacade storageFacade;

	public TaskManager() {
	}
	
	
	// return null if there is no task
	public synchronized Task getTask(String workerName) {
		if(stopped) {
			Task t = new Task();
			t.setType(Task.STOP);
			return t;
		}
		
		List<WebURL> urlList = storageFacade.getUrlStorage().get(taskSize);
		if(urlList.isEmpty()) {
			return null;
		}
		Task t = new Task();
		t.setUrls(urlList);
		t.setAssignee(workerName);
		return t;
	}
	
	public synchronized boolean hasMoreTask() {
		return storageFacade.getUrlStorage().hasUrlToVisit();
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


	public StorageFacade getStorageFacade() {
		return storageFacade;
	}


	public void setStorageFacade(StorageFacade storageFacade) {
		this.storageFacade = storageFacade;
	}

}
