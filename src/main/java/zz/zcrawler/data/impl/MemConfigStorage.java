package zz.zcrawler.data.impl;

import java.util.HashMap;

import zz.zcrawler.data.ConfigStorage;

public class MemConfigStorage implements ConfigStorage {

	private HashMap<String, Object> config = new HashMap<String, Object>();
	
	public MemConfigStorage() {
		config.put("", "");
	}
	
	@Override
	public String getString(String key) {
		return (String) config.get(key);
	}

	@Override
	public int getInt(String key) {
		return (int) config.get(key);
	}

	@Override
	public Object get(String key) {
		return config.get(key);
	}

	@Override
	public String[] getStringArray(String key) {
		return (String[]) config.get(key);
	}

}
