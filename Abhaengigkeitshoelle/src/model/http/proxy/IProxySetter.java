package model.http.proxy;

import java.net.HttpURLConnection;
import java.net.Proxy;

public interface IProxySetter {
	String getProxyLogin();
	String getProxyUrl();
	int getProxyPort();
	void proxyAuthenticate(HttpURLConnection connection);
	Proxy setSystemsProxySettings();
	boolean isProxySet();
}