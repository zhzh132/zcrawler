package zz.zcrawler;

import java.util.ArrayList;

import zz.zcrawler.worker.Worker;

public class WorkerRegister {

	private ArrayList<Worker> workers = new ArrayList<Worker>();
	
	public void register(Worker worker) {
		this.workers.add(worker);
	}
	
	public boolean allWorkersWaitingOrDead() {
		for(Worker w : workers) {
			if(!w.isWaitingOrDead()) {
				return false;
			}
		}
		return true;
	}
}
