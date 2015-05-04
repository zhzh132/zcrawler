package zz.zcrawler.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import zz.zcrawler.common.Site;
import zz.zcrawler.data.SiteDao;

public class TaskManager {

	private Queue<Task> taskQueue;
	private SiteDao siteDao;

	public TaskManager() {
		this.taskQueue = new LinkedList<Task>();
	}
	
	public void init() {
		List<Site> sites = siteDao.getAllSites();
		for(Site s : sites) {
			List<String> urls = s.getEntranceUrls();
			for(String u : urls) {
				Task t = new Task();
				t.setType(Task.LIST_PAGE);
				t.addUrl(u);
				// TODO: check if visited
				this.taskQueue.add(t);
			}
		}
	}
	
	public SiteDao getSiteDao() {
		return siteDao;
	}

	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}


	public Task getTask() {
		return taskQueue.poll();
	}
	
}
