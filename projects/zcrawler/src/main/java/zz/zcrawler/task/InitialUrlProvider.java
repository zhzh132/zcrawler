package zz.zcrawler.task;

import java.util.List;

import zz.zcrawler.url.WebURL;

public interface InitialUrlProvider {
	public List<WebURL> getInitialUrls();
}
