package zz.zcrawler.task;

import java.util.LinkedList;
import java.util.Queue;

import zz.zcrawler.url.URLStorage;

public class TaskManager {

	private Queue<Task> taskQueue;
	private int taskSize;
	private URLStorage urlStorage;

	public TaskManager() {
		this.taskQueue = new LinkedList<Task>();
	}
	
	
	public synchronized Task getTask() {
		return taskQueue.poll();
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
