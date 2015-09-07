package net.sephy.postman;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Sephy
 * @since: 2015-08-08
 */
public class PostmanTemplateTest {

	private static CloseableHttpClient httpClient;

	private static PostmanTemplate template;

	@BeforeClass
	public static void beforeClass() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		template = new PostmanTemplate();
		template.setHttpClient(httpClient);
	}

	@AfterClass
	public static void afterClass() {
		HttpClientUtils.closeQuietly(httpClient);
	}

	@Test
	public void testGet() throws Exception {

	}

	@Test
	public void testPost() throws Exception {

	}

	@Test
	public void testGetJSON() throws Exception {

	}

	@Test
	public void testPostJSON() throws Exception {

	}
}