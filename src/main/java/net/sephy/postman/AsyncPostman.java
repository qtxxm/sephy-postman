package net.sephy.postman;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.client.methods.ZeroCopyConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Future;

/**
 * @author Sephy
 * @since: 2015-09-17
 */
public class AsyncPostman {

	private HttpAsyncClient httpAsyncClient = HttpAsyncClientBuilder
			.create()
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36")
			.build();

	public void setHttpAsyncClient(HttpAsyncClient httpAsyncClient) {
		this.httpAsyncClient = httpAsyncClient;
	}

	public Future<File> download(String url, File fileToSave, FutureCallback<File> callback) {
		try {
			HttpAsyncRequestProducer producer = HttpAsyncMethods.createGet(url);
			FileDownloadConsumer consumer = new FileDownloadConsumer(fileToSave);
			Future<File> future = httpAsyncClient.execute(producer, consumer, callback);
			return future;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static class FileDownloadConsumer extends ZeroCopyConsumer<File> {

		public FileDownloadConsumer(File file) throws FileNotFoundException {
			super(file);
		}

		@Override
		protected File process(HttpResponse response, File file, ContentType contentType) throws Exception {
			return file;
		}
	}
}
