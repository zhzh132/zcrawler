package zz.zcrawler.data;

import java.util.List;

import zz.zcrawler.url.WebURL;

public interface URLStorage {

	void putUrlToVisit(WebURL url);
	void putUrlVisited(WebURL url);
	List<WebURL> get(int count);
	boolean isVisited(String url);
	boolean isVisited(WebURL url);
}
