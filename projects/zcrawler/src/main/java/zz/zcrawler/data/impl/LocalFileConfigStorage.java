package zz.zcrawler.data.impl;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import zz.zcrawler.data.ConfigStorage;

public class LocalFileConfigStorage implements ConfigStorage {

	public static final String CONFIG_FILE = "config.json";
	private JSONObject json;
	
	public void setJSON(String jsonStr) {
		json = new JSONObject(jsonStr);
	}
	
	@Override
	public JSONObject getConfig() {
		return json;
	}

	@Override
	public JSONObject reloadConfig() {
		try {
			String jsonStr = IOUtils.toString(new ClassPathResource(CONFIG_FILE).getInputStream());
			this.setJSON(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	


}
