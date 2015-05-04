package zz.zcrawler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zz.zcrawler.conf.CrawlerConfig;
import zz.zcrawler.task.Task;
import zz.zcrawler.task.TaskManager;

/**
 * Hello world!
 *
 */
public class App 
{
	static Log log = LogFactory.getLog(App.class);
	
	
	public static void main( String[] args )
    {
		log.debug("Crawler Start.");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//CrawlerConfig config = context.getBean(CrawlerConfig.class);
		//log.debug(config.getProperty("zcrawler.maxDepth"));
		
		TaskManager tm = context.getBean(TaskManager.class);
		tm.init();
		Task t = tm.getTask();
		
		log.debug(t.getUrls().get(0));
		
		context.close();
		log.debug("Crawler Stop.");
    }
}
