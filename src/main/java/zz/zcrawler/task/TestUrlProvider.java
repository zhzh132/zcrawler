package zz.zcrawler.task;

import java.util.ArrayList;
import java.util.List;

import zz.zcrawler.url.WebURL;

public class TestUrlProvider implements InitialUrlProvider {

	private List<WebURL> urls;
	
	public TestUrlProvider() {
		urls = new ArrayList<WebURL>();
		WebURL u = new WebURL();
		u.setURL("http://www.163.com");
		urls.add(u);
	}
	
	public List<WebURL> getInitialUrls() {
		return urls;
	}

}
