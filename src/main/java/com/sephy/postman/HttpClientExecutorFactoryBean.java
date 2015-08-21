package com.sephy.postman;

import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Sephy
 * @since: 2015-08-12
 */
public class HttpClientExecutorFactoryBean implements FactoryBean<Executor>, InitializingBean {

	private Executor executor;

	private HttpClient httpClient;

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public Executor getObject() throws Exception {
		return executor;
	}

	@Override
	public Class<?> getObjectType() {
		return Executor.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = Executor.newInstance(httpClient);
	}
}
