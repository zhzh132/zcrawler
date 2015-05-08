package zz.zcrawler.parser.impl;

import java.io.Serializable;
import java.util.List;

import zz.zcrawler.StorageFacade;
import zz.zcrawler.parser.ResultHandler;
import zz.zcrawler.url.WebURL;

public class LinkResultHandler implements ResultHandler {

	@Override
	public void handle(Serializable result) {
		if (result != null) {
			if (result instanceof List) {
				List<WebURL> list = (List<WebURL>) result;
				for (WebURL url : list) {
					StorageFacade.getInstance().putUrlToVisit(url);
				}
			}
		}
	}

}
