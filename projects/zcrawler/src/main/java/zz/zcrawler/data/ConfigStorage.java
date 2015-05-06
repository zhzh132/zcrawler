package zz.zcrawler.data;

public interface ConfigStorage {

	String getString(String key);
	String[] getStringArray(String key);
	int getInt(String key);
	Object get(String key);
}
