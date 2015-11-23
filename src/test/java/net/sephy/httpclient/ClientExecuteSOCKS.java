package net.sephy.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author Sephy
 * @since: 2015-10-28
 */
public class ClientExecuteSOCKS {

	public static void main(String[] args) throws Exception {
		Registry<ConnectionSocketFactory> reg = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", new MyConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
				reg);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setConnectionManager(cm).build();
		try {
			InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1",
					1080);
			HttpClientContext context = HttpClientContext.create();
			context.setAttribute("socks.address", socksaddr);

			HttpGet request = new HttpGet("http://www.google.com");

			System.out.println("Executing request " + request
					+ " via SOCKS proxy " + socksaddr);
			CloseableHttpResponse response = httpclient.execute(request,
					context);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
				EntityUtils.consume(response.getEntity());
			}
			finally {
				response.close();
			}
		}
		finally {
			httpclient.close();
		}
	}

	static class MyConnectionSocketFactory implements ConnectionSocketFactory {

		@Override
		public Socket createSocket(final HttpContext context)
				throws IOException {
			InetSocketAddress socksaddr = (InetSocketAddress) context
					.getAttribute("socks.address");
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
			return new Socket(proxy);
		}

		@Override
		public Socket connectSocket(final int connectTimeout,
				final Socket socket, final HttpHost host,
				final InetSocketAddress remoteAddress,
				final InetSocketAddress localAddress, final HttpContext context)
				throws IOException, ConnectTimeoutException {
			Socket sock;
			if (socket != null) {
				sock = socket;
			}
			else {
				sock = createSocket(context);
			}
			if (localAddress != null) {
				sock.bind(localAddress);
			}
			try {
				sock.connect(remoteAddress, connectTimeout);
			}
			catch (SocketTimeoutException ex) {
				throw new ConnectTimeoutException(ex, host,
						remoteAddress.getAddress());
			}
			return sock;
		}

	}

}
