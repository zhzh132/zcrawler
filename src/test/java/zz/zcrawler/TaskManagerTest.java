package zz.zcrawler;

import static org.junit.Assert.*;

import org.junit.Test;

import zz.zcrawler.data.impl.MemURLStorage;
import zz.zcrawler.task.Task;
import zz.zcrawler.task.TaskManager;

public class TaskManagerTest {

	@Test
	public void testStopTask() {
		TaskManager tm = new TaskManager();
		tm.stop();
		Task t = tm.getTask(null);
		assertEquals(Task.STOP, t.getType());
	}
	
	@Test
	public void testNullTask() {
		MemURLStorage urls = new MemURLStorage();
		StorageFacade facade = new StorageFacade();
		facade.setUrlStorage(urls);
		
		TaskManager tm = new TaskManager();
		tm.setStorageFacade(facade);
		Task t = tm.getTask(null);
		assertNull(t);
	}

}
