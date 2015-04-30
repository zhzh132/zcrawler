package zz.zcrawler.task;

import java.util.ArrayList;
import java.util.List;

import zz.zcrawler.url.WebURL;

public class TestUrlProvider implements InitialUrlProvider {

	public List<WebURL> getInitialUrls() {
		ArrayList<WebURL> urls = new ArrayList<WebURL>();
		WebURL u = new WebURL();
		u.setURL("www.163.com");
		urls.add(u);
		return urls;
	}

}
