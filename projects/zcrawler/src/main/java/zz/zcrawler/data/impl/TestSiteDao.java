package zz.zcrawler.data.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import zz.zcrawler.common.Site;
import zz.zcrawler.data.SiteDao;

public class TestSiteDao implements SiteDao {

	private ArrayList<Site> sites;
	
	public TestSiteDao() {
		sites = new ArrayList<Site>();
		Site s = new Site();
		s.setDomain("163.com");
		s.setActived(true);
		s.addEntranceUrl("http://www.163.com");
		s.setContentPageRegex("http://news\\.163\\.com/[0-9/]+/[A-Z0-9]+\\.html");
		sites.add(s);
	}
	
	@Override
	public List<Site> getAllSites() {
		return sites;
	}

}
