package model.http.urlconnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import model.http.proxy.IProxySetter;

public class HttpURLConnectionConfiguration implements ConnectionFactory {
	private static final String USERAGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; %s; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
	
	private final String method;
	private final String requestcharset;
	private final IProxySetter proxy;
	private final String lang;

	private boolean connect;
	
	public HttpURLConnectionConfiguration(IProxySetter proxy, String method, String requestcharset,
			 String lang) {
		super();
		this.requestcharset = requestcharset;
		this.proxy = proxy;
		this.lang = lang;
		this.method = method;
	}
	public HttpURLConnectionConfiguration(
			IProxySetter proxy, 
			String method, 
			String requestcharset,
			 String lang, boolean connect) {
		super();
		this.requestcharset = requestcharset;
		this.proxy = proxy;
		this.lang = lang;
		this.method = method;
		this.connect = connect;
	}
	/* (non-Javadoc)
	 * @see model.http.ConnectionFactory#establishConnection(java.net.URL)
	 */
	public URLConnection establishConnection(final URL url)
			throws IOException, ProtocolException {
		HttpURLConnection connection;
		if(proxy != null && !url.getHost().contains("localhost")) {
			connection = ( HttpURLConnection ) url.openConnection(proxy.setSystemsProxySettings());
		}
		else { 
			connection = ( HttpURLConnection ) url.openConnection();
		}
		connection.setRequestMethod( method );
		if(requestcharset != null)
			connection.setRequestProperty("Accept-Charset", this.requestcharset);
		connection.setRequestProperty("User-Agent", this.getAgent());
		connection.setRequestProperty("Content-Language", this.lang);
		if(proxy != null && !url.getHost().contains("localhost")) {
			proxy.proxyAuthenticate(connection);
		}
		if(connect)
			connection.connect();
		return connection;
	}
	public String getAgent() {
		return String.format(USERAGENT, lang);
	}
}
