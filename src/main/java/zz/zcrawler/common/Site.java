package zz.zcrawler.common;

import java.util.ArrayList;
import java.util.List;

public class Site {

	private String domain;
	private boolean isActived;
	private String contentPageRegex;
	private List<String> entranceUrls = new ArrayList<String>();
	
	public void addEntranceUrl(String url) {
		this.entranceUrls.add(url);
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public boolean isActived() {
		return isActived;
	}
	public void setActived(boolean isActived) {
		this.isActived = isActived;
	}
	public List<String> getEntranceUrls() {
		return entranceUrls;
	}
	public void setEntranceUrls(List<String> entranceUrls) {
		this.entranceUrls = entranceUrls;
	}

	public String getContentPageRegex() {
		return contentPageRegex;
	}

	public void setContentPageRegex(String contentPageRegex) {
		this.contentPageRegex = contentPageRegex;
	}
	
}
