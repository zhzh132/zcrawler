package zz.zcrawler.parser.impl;

import java.util.LinkedList;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.util.StringUtils;

import zz.zcrawler.StorageFacade;
import zz.zcrawler.parser.Parser;
import zz.zcrawler.parser.ResultHandler;
import zz.zcrawler.url.UrlResolver;
import zz.zcrawler.url.WebURL;

public class LinkParser implements Parser, BeanNameAware {

	@Override
	public void parse(WebURL pageUrl, String content, JSONObject siteConfig, ResultHandler handler) {
		int maxDepth = siteConfig.getInt("maxDepth");
		if (pageUrl.getDepth() < maxDepth) {
			Document doc = Jsoup.parse(content);
			LinkedList<WebURL> urls = extractLinks(doc, pageUrl);
			handler.handle(urls, siteConfig);
		}
	}
	
	private LinkedList<WebURL> extractLinks(Document doc, WebURL url) {
		LinkedList<WebURL> extracted = new LinkedList<WebURL>();
		Elements links = doc.getElementsByTag("a");
		
		for (Element link : links) {
			  String linkHref = link.attr("href");
			  linkHref = getFullUrl(url, linkHref);
			  
			  if(!StringUtils.isEmpty(linkHref)) {
				  WebURL u = new WebURL();
				  u.setURL(linkHref);
				  u.setDepth(url.getDepth() + 1);
				  if(StorageFacade.getInstance().shouldVisit(u)) {
					  extracted.add(u);
				  }
			  }
		}
		
		return extracted;
	}
	
	private String getFullUrl(WebURL url, String href) {
		if(StringUtils.isEmpty(href)) {
			return "";
		}
		String hrefLoweredCase = href.trim().toLowerCase();
		if(hrefLoweredCase.startsWith("#")) {
			return "";
		}
		if (hrefLoweredCase.contains("javascript:") || hrefLoweredCase.contains("mailto:") ||
	            hrefLoweredCase.contains("@")) {
			return "";
		}
		if(hrefLoweredCase.indexOf("://") > 0) {   // href is a full url
			return hrefLoweredCase;
		}
		return UrlResolver.resolveUrl(url, hrefLoweredCase);
	}

	@Override
	public boolean accept(WebURL pageUrl, String content, JSONObject siteConfig) {
		String acceptPattern = siteConfig.getJSONObject("parserConfig").getJSONObject(beanName).optString("acceptUrlPattern");
		if(StringUtils.isEmpty(acceptPattern)) {
			return true;
		}
		return pageUrl.getURL().matches(acceptPattern);
	}

	private String beanName;
	
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

}
