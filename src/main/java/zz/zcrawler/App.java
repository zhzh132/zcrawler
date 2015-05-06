package zz.zcrawler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zz.zcrawler.data.ConfigStorage;
import zz.zcrawler.data.URLStorage;
import zz.zcrawler.url.WebURL;

/**
 * Hello world!
 *
 */
public class App 
{
	static Log log = LogFactory.getLog(App.class);
	
	static ClassPathXmlApplicationContext context;
	
	public static void main( String[] args )
    {
		log.debug("Crawler Start.");
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		
		init();
		
		
		
		context.close();
		log.debug("Crawler Stop.");
    }
	
	public static void init() {
		// load sites to crawl
		ConfigStorage config = context.getBean(ConfigStorage.class);
		config.loadConfig();
		
		URLStorage urlStorage = context.getBean("memURLStorage", URLStorage.class);
		List<String> sites = config.getActiveDomains();
		for(String s : sites) {
			JSONObject site = config.getSite(s);
			JSONArray urls = site.getJSONArray("seed");
			for(int i = 0; i < urls.length(); i++) {
				String u = urls.getString(i);
				WebURL webUrl = new WebURL();
				webUrl.setURL(u);
				webUrl.setPageType(WebURL.LIST_PAGE);
				urlStorage.putUrlToVisit(webUrl);
			}
		}
		
	}
}
