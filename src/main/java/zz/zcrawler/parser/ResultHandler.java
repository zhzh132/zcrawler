package zz.zcrawler.parser;

import java.io.Serializable;

import org.json.JSONObject;

public interface ResultHandler {

	void handle(Serializable result, JSONObject siteConfig);
}
