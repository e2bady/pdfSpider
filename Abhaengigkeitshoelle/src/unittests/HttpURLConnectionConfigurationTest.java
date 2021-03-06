package unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URLConnection;

import model.http.proxy.ProxySetter;
import model.http.urlconnection.ConnectionFactory;
import model.http.urlconnection.HttpURLConnectionConfiguration;

import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;;

public class HttpURLConnectionConfigurationTest {
	@Test
	public final void testEstablishConnectionWithProxy() throws ProtocolException, MalformedURLException, IOException {
		ProxySetter mock = mock(ProxySetter.class);
		when(mock.setSystemsProxySettings()).thenReturn(Proxy.NO_PROXY);
		
		ConnectionFactory connectionfactory = new HttpURLConnectionConfiguration(
				mock, "GET", "UTF-8", "de");
		MockURLFactory factory = basicCheck(connectionfactory);
        assertTrue("Proxy was not set during openConnection, " +
				"or openConnection was never called.", factory.isCalledWithProxy());
		assertFalse("Proxy was set during openConnection, " +
				"or openConnection was never called.", factory.isCalledWithOutProxy());
	}

	private MockURLFactory basicCheck(ConnectionFactory connectionfactory)
			throws IOException, ProtocolException {
		MockURLFactory mockFactory = new MockURLFactory("HttpURLConnectionConfigurationTestFile.htm");
		URLConnection connection = connectionfactory.establishConnection(
				mockFactory.getUrl());
		
		((HttpURLConnection) verify(connection)).setRequestMethod( "GET" );
		verify(connection).setRequestProperty("User-Agent", connectionfactory.getAgent());
        verify(connection).setRequestProperty("Content-Language", "de");
        
        return mockFactory;
	}
	@Test
	public final void testEstablishConnectionWithOutProxy() throws ProtocolException, MalformedURLException, IOException {
		ConnectionFactory connectionfactory = new HttpURLConnectionConfiguration(
				null, "GET", "UTF-8", "de");
		MockURLFactory factory = basicCheck(connectionfactory);
        assertFalse("Proxy was not set during openConnection, " +
				"or openConnection was never called.", factory.isCalledWithProxy());
		assertTrue("Proxy was set during openConnection, " +
				"or openConnection was never called.", factory.isCalledWithOutProxy());
	}
}
