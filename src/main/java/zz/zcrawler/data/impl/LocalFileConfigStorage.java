package zz.zcrawler.data.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import zz.zcrawler.data.ConfigStorage;

public class LocalFileConfigStorage implements ConfigStorage {

	private static Log log = LogFactory.getLog(LocalFileConfigStorage.class); 
	
	public static final String CONFIG_FILE = "config.json";
	public static final String SITES = "sites";
	public static final String GLOBAL = "global";
	private JSONObject json;
	
	public void setJSON(String jsonStr) {
		json = new JSONObject(jsonStr);
	}
	
	@Override
	public JSONObject getGlobalConfig() {
		return json.optJSONObject(GLOBAL);
	}

	@Override
	public JSONObject loadConfig() {
		try {
			String jsonStr = IOUtils.toString(new ClassPathResource(CONFIG_FILE).getInputStream());
			this.setJSON(jsonStr);
		} catch (IOException e) {
			log.error("Error load config file: " + CONFIG_FILE, e);
		}
		return json;
	}

	@Override
	public JSONObject getSite(String domain) {
		JSONObject sites = json.getJSONObject(SITES);
		return sites.optJSONObject(domain);
	}

	@Override
	public List<String> getActiveDomains() {
		ArrayList<String> domains = new ArrayList<String>();
		
		JSONObject sites = json.getJSONObject(SITES);
		@SuppressWarnings("rawtypes")
		Iterator it = sites.keys();
		while(it.hasNext()) {
			String key = (String)it.next();
			if(sites.getJSONObject(key).optBoolean("isActive")) {
				domains.add(key);
			}
		}
		return domains;
	}
	


}
