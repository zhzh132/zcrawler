package zz.zcrawler.task;

import java.util.ArrayList;
import java.util.List;

import zz.zcrawler.url.WebURL;

public class Task {

	public static final String STOP = "STOP";
	public static final String NORMAL = "NORMAL";
	
	private String type = NORMAL;
	private List<WebURL> urls = new ArrayList<WebURL>();
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<WebURL> getUrls() {
		return urls;
	}
	
	public void setUrls(List<WebURL> urls) {
		this.urls = urls;
	}
	
	public void addUrl(WebURL wu) {
		this.urls.add(wu);
	}
	
	public void addUrl(String url) {
		WebURL u = new WebURL();
		u.setURL(url);
		this.addUrl(u);
	}

}
