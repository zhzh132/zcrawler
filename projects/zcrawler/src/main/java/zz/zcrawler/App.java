package zz.zcrawler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zz.zcrawler.data.ConfigStorage;
import zz.zcrawler.task.TaskManager;
import zz.zcrawler.url.WebURL;
import zz.zcrawler.worker.WorkerThread;

/**
 * Hello world!
 *
 */
public class App 
{
	static Log log = LogFactory.getLog(App.class);
	
	public static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static void main( String[] args )
    {
		log.debug("Crawler Start.");
		
		init();
		
		WorkerRegister register = context.getBean(WorkerRegister.class);
		startWorkers(register);
		
		TaskManager tm = context.getBean(TaskManager.class);
		while(tm.hasMoreTask() || !register.allWorkersWaitingOrDead()) {
			sleep(10);
		}
		tm.stop();
		sleep(10);
		context.close();
		log.debug("Crawler Stop.");
    }
	
	private static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
		}
	}
	
	private static void startWorkers(WorkerRegister register) {
		WorkerThread worker = createWorkerThread("Worker 1");
		register.register(worker);
		worker.start();
	}
	
	private static WorkerThread createWorkerThread(String name) {
		WorkerThread worker = new WorkerThread();
		worker.setName(name);
		worker.setTaskManager(context.getBean(TaskManager.class));
		return worker;
	}

	public static void init() {
		// load sites to crawl
		ConfigStorage config = context.getBean(ConfigStorage.class);
		config.loadConfig();
		
		int taskSize = config.getGlobalConfig().optInt("taskSize");
		if(taskSize > 0) {
			TaskManager tm = context.getBean(TaskManager.class);
			tm.setTaskSize(taskSize);
		}
		
		List<String> sites = config.getActiveDomains();
		for(String s : sites) {
			JSONObject site = config.getSite(s);
			JSONArray urls = site.getJSONArray("seed");
			for(int i = 0; i < urls.length(); i++) {
				String u = urls.getString(i);
				WebURL webUrl = new WebURL();
				webUrl.setURL(u);
				webUrl.setPageType(WebURL.LIST_PAGE);
				StorageFacade.getInstance().putUrlToVisit(webUrl);
			}
		}
		
	}
}
