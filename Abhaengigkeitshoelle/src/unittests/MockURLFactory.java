package unittests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLStreamHandler;

import model.Lazy;

import org.mockito.Mockito;

public class MockURLFactory extends Lazy {
	private static final String DELIMITER = ";";
	private static final String SEPERATOR = "=";
	private static final String TESTPREFIX = "testprefix";
	private boolean calledWithProxy = false;
	private boolean calledWithOutProxy = false;
	private HttpURLConnection mockConnection;
	private URLStreamHandler handler;
	private URL url;
	private final String filename;

	public MockURLFactory(String filename) {
		super();
		this.filename = filename;
	}
	@Override
	protected boolean load() {
		try {
			this.setMockConnection(createMockConnection(this.filename));
			this.setHandler(createMockURLStreamHandler(mockConnection));
			this.setUrl(new URL("http://foo.bar", "foo.bar", 80, "", handler));
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
	}
	private HttpURLConnection createMockConnection(final String filename) {
		try {
			final File file = new File("testdata/" + filename);
			HttpURLConnection mockConnection = Mockito
					.mock(HttpURLConnection.class);
			FileInputStream stream;
			stream = new FileInputStream(file);
			String contentValue = "someotherprefix2" + SEPERATOR + "b"
					+ DELIMITER + TESTPREFIX + SEPERATOR + "c" + DELIMITER
					+ "charset" + SEPERATOR + "UTF-8" + DELIMITER
					+ "someotherprefix" + SEPERATOR + "a" + DELIMITER;
			Mockito.when(mockConnection.getHeaderField("Content-Type"))
					.thenReturn(contentValue);
			Mockito.when(mockConnection.getInputStream()).thenReturn(stream);
			return mockConnection;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private URLStreamHandler createMockURLStreamHandler(
			final HttpURLConnection mockConnection) {
		final URLStreamHandler handler = new URLStreamHandler() {
			@Override
			protected HttpURLConnection openConnection(final URL arg0)
					throws IOException {
				calledWithOutProxy = true;
				return mockConnection;
			}

			@Override
			protected HttpURLConnection openConnection(URL u, Proxy p)
					throws IOException {
				calledWithProxy = true;
				return mockConnection;
			}
		};
		return handler;
	}

	public boolean isCalledWithProxy() {
		return calledWithProxy;
	}

	public boolean isCalledWithOutProxy() {
		return calledWithOutProxy;
	}

	public HttpURLConnection getMockConnection() {
		super.lazyLoad();
		return mockConnection;
	}

	private void setMockConnection(HttpURLConnection mockConnection) {
		this.mockConnection = mockConnection;
	}

	public URLStreamHandler getHandler() {
		super.lazyLoad();
		return handler;
	}

	private void setHandler(URLStreamHandler handler) {
		this.handler = handler;
	}

	public URL getUrl() {
		super.lazyLoad();
		return url;
	}

	private void setUrl(URL url) {
		this.url = url;
	}
}
