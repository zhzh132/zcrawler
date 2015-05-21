package zz.zcrawler.parser.impl;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.util.StringUtils;

import zz.zcrawler.StorageFacade;
import zz.zcrawler.parser.ResultHandler;
import zz.zcrawler.url.WebURL;

public class LinkResultHandler implements ResultHandler, BeanNameAware {

	@Override
	public void handle(Serializable result, JSONObject siteConfig) {
		String acceptPattern = siteConfig.getJSONObject("handlerConfig").getJSONObject(beanName).optString("acceptUrlPattern");
		
		if (result != null) {
			if (result instanceof List) {
				@SuppressWarnings("unchecked")
				List<WebURL> list = (List<WebURL>) result;
				for (WebURL url : list) {
					if (StringUtils.isEmpty(acceptPattern) || url.getURL().matches(acceptPattern)) {
						StorageFacade.getInstance().putUrlToVisit(url);
					}
				}
			}
		}
	}

	private String beanName;
	
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

}
