package zz.zcrawler.url;

import java.util.List;

public interface URLStorage {

	void putUrlToVisit(WebURL url);
	void putUrlVisited(WebURL url);
	List<WebURL> get(int count);
	boolean isVisited(String url);
	boolean isVisited(WebURL url);
}
