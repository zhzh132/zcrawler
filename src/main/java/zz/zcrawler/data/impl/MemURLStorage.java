package zz.zcrawler.data.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import zz.zcrawler.data.URLStorage;
import zz.zcrawler.url.WebURL;

public class MemURLStorage implements URLStorage {

	private List<WebURL> urlToVisit;
	private Set<String> urlVisited;
	
	public MemURLStorage() {
		this.urlToVisit = Collections.synchronizedList(new LinkedList<WebURL>());
		this.urlVisited = Collections.synchronizedSet(new HashSet<String>());
	}
	
	@Override
	public void putUrlToVisit(WebURL url) {
		if (!this.urlToVisit.contains(url)) {
			this.urlToVisit.add(url);
		}
	}

	@Override
	public List<WebURL> get(int count) {
		LinkedList<WebURL> list = new LinkedList<WebURL>();
		for (int i = 0; i < count; i++) {
			if (!this.urlToVisit.isEmpty()) {
				list.add(this.urlToVisit.remove(0));
			} else {
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

	@Override
	public boolean hasUrlToVisit() {
		return !this.urlToVisit.isEmpty();
	}

}
