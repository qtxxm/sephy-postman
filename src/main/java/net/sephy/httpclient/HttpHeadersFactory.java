package net.sephy.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Sephy
 * @since: 2015-09-17
 */
public class HttpHeadersFactory implements FactoryBean<List<Header>>, InitializingBean {

	private List<Header> headers = new ArrayList<Header>();

	private Properties properties;

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	@Override
	public List<Header> getObject() throws Exception {
		return headers;
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (properties != null) {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				headers.add(new BasicHeader(entry.getKey().toString(), entry.getValue().toString()));
			}
		}
	}
}
