package zz.zcrawler.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CrawlerConfig {

	private static Log log = LogFactory.getLog(CrawlerConfig.class);
	private Properties pro = new Properties();
	
	public CrawlerConfig() {
		this.loadProperties("crawler.properties");
	}
	
	private void loadProperties(String file) {
		FileInputStream in = null;
		try {
			String CONFIG_PATH= ClassLoader.getSystemResource(file)
					.getPath();
			in = new FileInputStream(CONFIG_PATH);
			pro.load(in);
			log.debug("Properties loaded form " + file);
		} catch (Exception e) {
			log.error("Error load properties from " + file, e);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public String getProperty(String key){
		return pro.getProperty(key);
	}
}
