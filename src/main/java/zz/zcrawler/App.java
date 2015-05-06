package zz.zcrawler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zz.zcrawler.common.Site;
import zz.zcrawler.data.SiteDao;
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
		SiteDao siteDao = context.getBean("testSiteDao", SiteDao.class);
		URLStorage urlStorage = context.getBean("memURLStorage", URLStorage.class);
		List<Site> sites = siteDao.getAllSites();
		for(Site s : sites) {
			List<String> urls = s.getEntranceUrls();
			for(String u : urls) {
				WebURL webUrl = new WebURL();
				webUrl.setURL(u);
				webUrl.setPageType(WebURL.LIST_PAGE);
				urlStorage.putUrlToVisit(webUrl);
			}
		}
	}
}
