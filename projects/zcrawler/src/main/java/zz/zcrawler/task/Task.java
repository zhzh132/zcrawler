package zz.zcrawler.task;

import java.util.ArrayList;
import java.util.List;

import zz.zcrawler.url.WebURL;

public class Task {

	public static final String STOP = "STOP";
	public static final String NORMAL = "NORMAL";
	
	private String type = NORMAL;
	private List<WebURL> urls = new ArrayList<WebURL>();
	private String assignee;
	
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

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Task ---- ");
		sb.append("Type: ").append(this.type).append("  ");
		sb.append("Size: ").append(urls.size());
		if(this.urls.size() > 0) {
			sb.append("--------").append(System.lineSeparator());
			for(WebURL u : urls) {
				sb.append(u.getURL()).append(System.lineSeparator());
			}
			sb.append("--------").append(System.lineSeparator());
		}
		return sb.toString();
	}

}
