package zz.zcrawler.data;

import java.util.List;

import org.json.JSONObject;

public interface ConfigStorage {

	JSONObject getGlobalConfig();
	JSONObject loadConfig();
	JSONObject getSite(String domain);
	List<String> getActiveDomains();
}
