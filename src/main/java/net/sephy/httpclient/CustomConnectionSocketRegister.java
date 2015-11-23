package net.sephy.httpclient;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.net.ssl.SSLContext;

/**
 * @author Sephy
 * @since: 2015-09-18
 */
public class CustomConnectionSocketRegister implements FactoryBean<Registry<ConnectionSocketFactory>>, InitializingBean {

    private Registry<ConnectionSocketFactory> registry;

    @Override
    public Registry<ConnectionSocketFactory> getObject() throws Exception {
        return registry;
    }

    @Override
    public Class<?> getObjectType() {
        return Registry.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PlainConnectionSocketFactory httpSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        SSLContext sslContext = SSLContexts.createDefault();
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                new DefaultHostnameVerifier(PublicSuffixMatcherLoader.getDefault()));
        Socks5SocketFactory socketFactory = new Socks5SocketFactory(sslContext);
        registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", httpSocketFactory).register("https", sslSocketFactory).register("socks", socketFactory).build();
    }
}
