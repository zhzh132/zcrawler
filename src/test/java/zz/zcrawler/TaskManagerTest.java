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
		TaskManager tm = new TaskManager();
		MemURLStorage urls = new MemURLStorage();
		tm.setUrlStorage(urls);
		Task t = tm.getTask(null);
		assertNull(t);
	}

}
