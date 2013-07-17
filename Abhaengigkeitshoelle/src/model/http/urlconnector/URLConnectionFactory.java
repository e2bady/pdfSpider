package model.http.urlconnector;

import java.net.URL;

import model.http.urlconnection.ConnectionFactory;
import model.http.valueextractor.HttpValueExtractor;

public class URLConnectionFactory implements IURLConnectionFactory {
	private HttpValueExtractor charsetExtractor;
	private ConnectionFactory connectionFactory;

	public URLConnectionFactory(ConnectionFactory connectionFactory, 
			HttpValueExtractor charsetExtractor) {
		this.charsetExtractor = charsetExtractor;
		this.connectionFactory = connectionFactory;
	}
	/* (non-Javadoc)
	 * @see model.http.IURLConnectionFactory#create(java.net.URL)
	 */
	public URLConnector create(URL url) {
		return new URLConnectorImpl(this.connectionFactory, this.charsetExtractor, url);
	}
}
