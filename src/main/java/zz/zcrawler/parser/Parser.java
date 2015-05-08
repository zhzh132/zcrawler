package zz.zcrawler.parser;

import java.io.Serializable;

import org.json.JSONObject;

import zz.zcrawler.url.WebURL;

public interface Parser {

	Serializable parse(WebURL pageUrl, String content, JSONObject siteConfig);
}
