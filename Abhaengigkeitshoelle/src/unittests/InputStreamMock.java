package unittests;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

final class InputStreamMock extends InputStream {
	private final String test;
	private int index;
	private String charset;
	private byte[] bytes = null;
	
	public InputStreamMock(String test, String charset) {
		this.test = test;
		this.charset = charset;
	}

	private void initBytes(String charset) throws UnsupportedEncodingException {
		this.bytes = this.test.getBytes(charset);
	}
	
	@Override
	public int read() throws IOException {
		if(bytes == null)
			initBytes(charset);
		if(index < bytes.length) {
			return bytes[index++];
		}
		return -1;
	}
}