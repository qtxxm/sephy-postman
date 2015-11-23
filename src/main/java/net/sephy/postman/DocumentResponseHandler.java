package net.sephy.postman;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Sephy
 * @since: 2015-09-16
 */
public class DocumentResponseHandler extends AbstractResponseHandler<Document> {

	private String baseUri;

	private String charset;

    public DocumentResponseHandler() {
    }

    public DocumentResponseHandler(String baseUri, String charset) {
		this.baseUri = baseUri;
		this.charset = charset;
	}

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
	public Document handleEntity(HttpEntity entity) throws IOException {
		String charset = this.charset == null ? entity.getContentEncoding().getValue() : this.charset;
		return Jsoup.parse(entity.getContent(), charset, baseUri);
	}
}
