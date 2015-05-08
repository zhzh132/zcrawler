package zz.zcrawler.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import zz.zcrawler.url.WebURL;

public class NetUtils {

	private static Log log = LogFactory.getLog(NetUtils.class);
	
	public static String getPageString(WebURL url, JSONObject requestConfig) throws Exception {
		HttpGet httpget = new HttpGet(url.getURL());
		if(requestConfig != null) {
			RequestConfig.Builder configBuilder = RequestConfig.custom();
			
			// set proxy
			JSONObject proxyConfig = requestConfig.optJSONObject("proxy");
			if(proxyConfig != null) {
				String proxyHost = proxyConfig.getString("host");
				int proxyPort = proxyConfig.getInt("port");
				HttpHost proxy = new HttpHost(proxyHost, proxyPort);
				configBuilder.setProxy(proxy);
			}
			
			// TODO: set headers
			
			RequestConfig config = configBuilder.build();
			httpget.setConfig(config);
		}
		return getPageString(httpget);
	}
	
	public static String getPageString(HttpGet httpget) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			    @Override
			    public String handleResponse(
			            final HttpResponse response) throws ClientProtocolException, IOException {
			        int status = response.getStatusLine().getStatusCode();
			        if (status >= 200 && status < 300) {
			            HttpEntity entity = response.getEntity();
			            return entity != null ? EntityUtils.toString(entity) : null;
			        } else {
			            throw new ClientProtocolException("Unexpected response status: " + status);
			        }
			    }

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		} finally {
			httpclient.close();
		}
	}
}
