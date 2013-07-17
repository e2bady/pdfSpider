package model.http.proxy;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;


public class ProxySetter implements IProxySetter {
	private final String proxyLogin;
	private final String proxyUrl;
	private final int proxyPort;
	
	public Proxy setSystemsProxySettings() {
		if(this.isProxySet()) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.getProxyUrl(), this.getProxyPort()));
			return proxy;
		}
		return null;
	}
	public void proxyAuthenticate(HttpURLConnection connection) {
		if(this.isProxySet()) {
			Authenticator.setDefault(new PasswordAuthenticator(this.getProxyLogin()));
		}
	}
	public ProxySetter(String proxyLogin, String proxyUrl, int proxyPort) {
		super();
		this.proxyLogin = proxyLogin;
		this.proxyUrl = proxyUrl;
		this.proxyPort = proxyPort;
	}
	public String getProxyLogin() {
		return this.proxyLogin;
	}
	public boolean isProxySet() {
		return this.proxyLogin != null && this.getProxyUrl() != null && this.getProxyPort() > -1;
	}
	public String getProxyUrl() {
		return this.proxyUrl;
	}
	public int getProxyPort() {
		return this.proxyPort;
	}
}
