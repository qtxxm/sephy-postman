package net.sephy.postman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;

/**
 * @author Sephy
 * @since: 2015-09-16
 */
public class FileStreamResponseHandler extends AbstractResponseHandler<File> {

	private File file;

	public FileStreamResponseHandler(String path) {
		file = new File(path);
	}

	public FileStreamResponseHandler(File file) {
		this.file = file;
	}

	@Override
	public File handleEntity(HttpEntity entity) throws IOException {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			entity.writeTo(outputStream);
		}
		finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (Exception e) {

				}
			}
		}
		return file;
	}
}
