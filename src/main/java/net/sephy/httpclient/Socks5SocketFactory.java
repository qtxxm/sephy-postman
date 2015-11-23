package net.sephy.httpclient;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

/**
 * @author Sephy
 * @since: 2015-09-18
 */
public class Socks5SocketFactory extends SSLConnectionSocketFactory {

    private InetSocketAddress socketAddress;

	public Socks5SocketFactory(SSLContext sslContext) {
		super(sslContext);
	}

	@Override
	public Socket createSocket(final HttpContext context) throws IOException {
		InetSocketAddress address = (InetSocketAddress) context.getAttribute("socks.address");
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, address);
		return new Socket(proxy);
	}
}
