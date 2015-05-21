package zz.zcrawler.parser;

import org.json.JSONObject;

import zz.zcrawler.url.WebURL;

public interface Parser {

	void parse(WebURL pageUrl, String content, JSONObject siteConfig, ResultHandler handler);
	
	boolean accept(WebURL pageUrl, String content, JSONObject siteConfig);
}
