package zz.zcrawler;

import static org.junit.Assert.*;

import org.junit.Test;

import zz.zcrawler.url.UrlResolver;
import zz.zcrawler.url.WebURL;

public class UrlResolverTest {

	@Test
	public void testBaseUrl() {
		String url = "http://www.stackoverflow.com/";
		assertEquals("http://www.stackoverflow.com/", UrlResolver.getBaseUrl(url));
		
		url = "http://www.stackoverflow.com";
		assertEquals("http://www.stackoverflow.com/", UrlResolver.getBaseUrl(url));
		
		url = "http://www.stackoverflow.com/test.html";
		assertEquals("http://www.stackoverflow.com/", UrlResolver.getBaseUrl(url));
		
		url = "http://www.stackoverflow.com/abc/test.html";
		assertEquals("http://www.stackoverflow.com/abc/", UrlResolver.getBaseUrl(url));
		
		url = "http://stackoverflow.com/questions/4826061/what-is-the-fastest-way-to-get-the-domain-host-name-from-a-url";
		assertEquals("http://stackoverflow.com/questions/4826061/", UrlResolver.getBaseUrl(url));
	}
	
	@Test
	public void testResolveUrl() {
		String url;
		WebURL web;
		String relativeUrl;
		
		url = "http://www.stackoverflow.com";
		web = new WebURL();
		web.setURL(url);
		relativeUrl = "test.html";
		assertEquals("http://www.stackoverflow.com/test.html", UrlResolver.resolveUrl(web, relativeUrl));
		
		url = "http://www.stackoverflow.com/";
		web = new WebURL();
		web.setURL(url);
		relativeUrl = "test.html";
		assertEquals("http://www.stackoverflow.com/test.html", UrlResolver.resolveUrl(web, relativeUrl));
		
		url = "http://www.stackoverflow.com/abc/";
		web = new WebURL();
		web.setURL(url);
		relativeUrl = "test.html";
		assertEquals("http://www.stackoverflow.com/abc/test.html", UrlResolver.resolveUrl(web, relativeUrl));
		
		url = "http://www.stackoverflow.com/abc/";
		web = new WebURL();
		web.setURL(url);
		relativeUrl = "/test.html";
		assertEquals("http://www.stackoverflow.com/test.html", UrlResolver.resolveUrl(web, relativeUrl));
		
		url = "http://www.stackoverflow.com/abc/index.htm";
		web = new WebURL();
		web.setURL(url);
		relativeUrl = "/def/test.html";
		assertEquals("http://www.stackoverflow.com/def/test.html", UrlResolver.resolveUrl(web, relativeUrl));
		
		url = "http://www.stackoverflow.com/abc/index.htm";
		web = new WebURL();
		web.setURL(url);
		relativeUrl = "def/test.html";
		assertEquals("http://www.stackoverflow.com/abc/def/test.html", UrlResolver.resolveUrl(web, relativeUrl));
	}

}
