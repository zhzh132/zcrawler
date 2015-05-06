package zz.zcrawler.data.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import zz.zcrawler.data.URLStorage;
import zz.zcrawler.url.WebURL;

public class MemURLStorage implements URLStorage {

	private LinkedList<WebURL> urlToVisit;
	private HashSet<String> urlVisited;
	
	public MemURLStorage() {
		this.urlToVisit = new LinkedList<WebURL>();
		this.urlVisited = new HashSet<String>();
	}
	
	@Override
	public void putUrlToVisit(WebURL url) {
		this.urlToVisit.add(url);

	}

	@Override
	public List<WebURL> get(int count) {
		LinkedList<WebURL> list = new LinkedList<WebURL>();
		for(int i = 0; i < count; i++) {
			if(!this.urlToVisit.isEmpty()) {
				list.add(this.urlToVisit.poll());
			}
			else {
				break;
			}
		}
		return list;
	}

	@Override
	public boolean isVisited(String url) {
		return this.urlVisited.contains(url);
	}

	@Override
	public boolean isVisited(WebURL url) {
		return this.urlVisited.contains(url.getURL());
	}

	@Override
	public void putUrlVisited(WebURL url) {
		this.urlVisited.add(url.getURL());
		
	}

}
