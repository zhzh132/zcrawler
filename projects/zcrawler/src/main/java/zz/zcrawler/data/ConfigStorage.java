package zz.zcrawler.data;

import org.json.JSONObject;

public interface ConfigStorage {

	JSONObject getConfig();
	JSONObject reloadConfig();
}
