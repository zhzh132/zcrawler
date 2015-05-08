package zz.zcrawler.parser.impl;

import java.io.Serializable;
import java.util.LinkedList;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import zz.zcrawler.StorageFacade;
import zz.zcrawler.parser.Parser;
import zz.zcrawler.url.UrlResolver;
import zz.zcrawler.url.WebURL;

public class LinkParser implements Parser {

	@Override
	public Serializable parse(WebURL pageUrl, String content, JSONObject siteConfig) {
		int maxDepth = siteConfig.getInt("maxDepth");
		if (pageUrl.getDepth() < maxDepth) {
			Document doc = Jsoup.parse(content);
			return extractLinks(doc, pageUrl);
		}
		return null;
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
		if(hrefLoweredCase.indexOf("://") > 0) {   // href is full url
			return hrefLoweredCase;
		}
		return UrlResolver.resolveUrl(url, hrefLoweredCase);
	}

}
