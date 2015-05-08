package zz.zcrawler.worker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import zz.zcrawler.App;
import zz.zcrawler.StorageFacade;
import zz.zcrawler.parser.Parser;
import zz.zcrawler.parser.ResultHandler;
import zz.zcrawler.task.Task;
import zz.zcrawler.task.TaskManager;
import zz.zcrawler.url.WebURL;
import zz.zcrawler.utils.NetUtils;

public class WorkerThread extends Thread implements Worker {

	private static Log log = LogFactory.getLog(WorkerThread.class);
	
	private byte status = Worker.WAITING;
	private TaskManager taskManager;
	
	@Override
	public boolean isWaitingOrDead() {
		return status == Worker.WAITING || status == Worker.DEAD;
	}
	
	@Override
	public void run() {
		log.debug(this.getName() + " started.");
		
		while (true) {
			this.status = Worker.WAITING;
			Task task = taskManager.getTask(this.getName());
			if (task != null) {
				log.debug("Got task " + task.toString());
				if(task.getType().equals(Task.STOP)) {
					break;
				}
				this.status = Worker.WORKING;
				doTask(task);
			} else {
				// wait 1 second
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		this.status = Worker.DEAD;
		log.debug(this.getName() + " stopped.");
	}
	
	private void doTask(Task task) {
		for(WebURL url : task.getUrls()) {
			if(!StorageFacade.getInstance().shouldVisit(url)) {
				continue;
			}
			
			try {
				JSONObject siteConfig = StorageFacade.getInstance().getSiteConfig(url.getDomain());
				
				log.debug(this.getName() + ": downloading " + url);
				String content = NetUtils.getPageString(url, siteConfig.optJSONObject("requestConfig"));
				
				JSONArray parsers = siteConfig.optJSONArray("parsers");
				if(parsers != null) {
					for(int i = 0; i < parsers.length(); i++) {
						String parserBean = parsers.getJSONObject(i).getString("parserBean");
						String handlerBean = parsers.getJSONObject(i).getString("handlerBean");
						Parser parser = App.context.getBean(parserBean, Parser.class);
						ResultHandler handler = App.context.getBean(handlerBean, ResultHandler.class);
						handler.handle(parser.parse(url, content, siteConfig));
					}
				}
				
				StorageFacade.getInstance().putUrlVisited(url);
			} catch (Exception e) {
				log.error("Error processing url: " + url, e);
			}
		}
	}
	
	
	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	@Override
	public byte getStatus() {
		return this.status;
	}

}
