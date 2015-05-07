package zz.zcrawler.worker;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import zz.zcrawler.data.ConfigStorage;
import zz.zcrawler.data.URLStorage;
import zz.zcrawler.task.Task;
import zz.zcrawler.task.TaskManager;
import zz.zcrawler.url.UrlResolver;
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
			if(!shouldVisit(url)) {
				continue;
			}
			
			try {
				log.debug(this.getName() + ": downloading " + url);
				Document doc = Jsoup.connect(url.getURL()).get();
				this.urlStorage.putUrlVisited(url);
				
				JSONObject siteConfig = this.configStorage.getSite(url.getDomain());
				int maxDepth = siteConfig.getInt("maxDepth");
				if (url.getDepth() < maxDepth) {
					extractLinks(doc, url);
				}
			} catch (IOException e) {
				log.error("Error processing url: " + url, e);
			}
		}
	}

	private void extractLinks(Document doc, WebURL url) {
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			  String linkHref = link.attr("href");
			  linkHref = getFullUrl(url, linkHref);
			  
			  if(!StringUtils.isEmpty(linkHref)) {
				  WebURL u = new WebURL();
				  u.setURL(linkHref);
				  u.setDepth(url.getDepth() + 1);
				  if(shouldVisit(u)) {
					 this.urlStorage.putUrlToVisit(u); 
				  }
			  }
		}
	}
	
	private String getFullUrl(WebURL url, String href) {
		if(StringUtils.isEmpty(href)) {
			return "";
		}
		String hrefLoweredCase = href.trim().toLowerCase();
		if(hrefLoweredCase.startsWith("#")) {
			return "";
		}
		if (hrefLoweredCase.contains("javascript:") || hrefLoweredCase.contains("mailto:") ||
	            hrefLoweredCase.contains("@")) {
			return "";
		}
		if(hrefLoweredCase.indexOf("://") > 0) {   // href is full url
			return hrefLoweredCase;
		}
		return UrlResolver.resolveUrl(url, hrefLoweredCase);
	}

	private boolean shouldVisit(WebURL url) {
		JSONObject siteConfig = this.configStorage.getSite(url.getDomain());
		if(siteConfig == null) {
			return false;
		}
		if(!siteConfig.getBoolean("isActive")) {
			return false;
		}
		if(this.urlStorage.isVisited(url)) {
			return false;
		}
		return true;
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
