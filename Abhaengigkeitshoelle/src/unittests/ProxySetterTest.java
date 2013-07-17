package unittests;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import model.http.proxy.IProxySetter;
import model.http.proxy.ProxySetter;

import org.junit.Test;

public class ProxySetterTest {
	@Test
	public final void testSetSystemsProxySettings() {
		IProxySetter proxySetter = new ProxySetter("xtfix4:start123", "149.211.177.96", 8080);
		assertEquals("proxylogin was not set correctly.", "xtfix4:start123", proxySetter.getProxyLogin());
		assertEquals("proxyurl was not set correctly.", "149.211.177.96", proxySetter.getProxyUrl());
		assertEquals("proxyport was not set correctly.", 8080, proxySetter.getProxyPort());
		
		Proxy proxy = proxySetter.setSystemsProxySettings();
		assertTrue("Proxy was not created right.", proxy.equals(new Proxy(Type.HTTP, new InetSocketAddress("149.211.177.96", 8080))));
		//Lets try ;)
		proxySetter.proxyAuthenticate(null);
	}
}
