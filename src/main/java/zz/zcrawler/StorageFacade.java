package zz.zcrawler;

import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import zz.zcrawler.data.ConfigStorage;
import zz.zcrawler.data.URLStorage;
import zz.zcrawler.url.WebURL;

public class StorageFacade implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static StorageFacade getInstance() {
		return context.getBean(StorageFacade.class);
	}
	
	private ConfigStorage configStorage;
	
	private URLStorage urlStorage;

	
	public JSONObject getSiteConfig(String domain) {
		return this.configStorage.getSite(domain);
	}
	
	public JSONObject getGlobalConfig() {
		return this.configStorage.getGlobalConfig();
	}
	
	public void putUrlToVisit(WebURL url) {
		this.urlStorage.putUrlToVisit(url);
	}
	
	public void putUrlVisited(WebURL url) {
		this.urlStorage.putUrlVisited(url);
	}
	
	public boolean isVisited(WebURL url) {
		return this.urlStorage.isVisited(url);
	}
	
	public boolean shouldVisit(WebURL url) {
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
	
	public ConfigStorage getConfigStorage() {
		return configStorage;
	}

	public void setConfigStorage(ConfigStorage configStorage) {
		this.configStorage = configStorage;
	}

	public URLStorage getUrlStorage() {
		return urlStorage;
	}

	public void setUrlStorage(URLStorage urlStorage) {
		this.urlStorage = urlStorage;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		StorageFacade.context = applicationContext;
	}
	
	
}
