package model.http.urlconnector;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import model.Lazy;
import model.http.urlconnection.ConnectionFactory;
import model.http.valueextractor.HttpValueExtractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLConnectorImpl extends Lazy implements URLConnector {

	private static final Logger log = (Logger) LoggerFactory
			.getLogger(URLConnectorImpl.class);
	private final HttpValueExtractor charsetExtractor;
	private final ConnectionFactory connectionFactory;
	private final URL url;
	
	private InputStream response;
	private String charset;
	
	public URLConnectorImpl(ConnectionFactory connectionFactory, 
			HttpValueExtractor charsetExtractor, URL url) {
		this.charsetExtractor = charsetExtractor;
		this.connectionFactory = connectionFactory;
		this.url = url;
	}
	private void connect() throws ProtocolException, IOException {
		URLConnection connection = connectionFactory.establishConnection(url);
		this.setResponse(connection.getInputStream());
		String contentType = connection.getHeaderField("Content-Type");
		this.setCharset(charsetExtractor.getValue(contentType));
		log.debug("charset: " + charset + "; contenttype: " + contentType);
	}
	@Override
	protected boolean load() {
		try {
			connect();
		} catch (ProtocolException e) {
			log.error("Connect threw ProtocolException for URL: " + this.url != null ? this.url.toExternalForm() : "null");
			if(log.isDebugEnabled())
				e.printStackTrace();
		} catch (IOException e) {
			log.error("Connect threw IOException for URL: " + this.url != null ? this.url.toExternalForm() : "null");
			if(log.isDebugEnabled())
				e.printStackTrace();
		} catch (Exception e) {
			log.error("Connect threw IOException for URL: " + this.url != null ? this.url.toExternalForm() : "null");
			if(log.isDebugEnabled())
				e.printStackTrace();
		} 
		return false;
	}
	
	/* (non-Javadoc)
	 * @see model.http.URLConnector#getResponse()
	 */
	public InputStream getResponse() {
		super.lazyLoad();
		return response;
	}
	private void setResponse(InputStream response) {
		this.response = response;
	}
	/* (non-Javadoc)
	 * @see model.http.URLConnector#getCharset()
	 */
	public String getCharset() {
		super.lazyLoad();
		return charset;
	}
	private void setCharset(String charset) {
		this.charset = charset;
	}
}
