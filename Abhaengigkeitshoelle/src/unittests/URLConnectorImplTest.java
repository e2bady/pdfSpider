package unittests;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import model.http.urlconnection.ConnectionFactory;
import model.http.urlconnector.URLConnector;
import model.http.urlconnector.URLConnectorImpl;
import model.http.valueextractor.HttpValueExtractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;


import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class URLConnectorImplTest {
	private static final String UTF_8 = "UTF-8";
	@Mock(answer = Answers.RETURNS_MOCKS) private ConnectionFactory connectionFactory;
	@Mock private HttpValueExtractor valueExtractor;
	@Mock private URLConnection connection;
	private InputStream is;
	
	@Before
	public void setUp() throws Exception {
		is = new InputStreamMock("TestString bloedsinn und so.", UTF_8);
		initMocks(this);
		when(
					connectionFactory.establishConnection(any(URL.class)))
					.thenReturn(connection);
		when(connection.getInputStream()).thenReturn(is);
		when(valueExtractor.getValue(anyString())).thenReturn(UTF_8);
	}
	
	@Test
	public final void testLoad() throws MalformedURLException {
		URLConnector connector = new URLConnectorImpl(connectionFactory, valueExtractor, new URL("http://www.google.com"));
		assertEquals(connector.getResponse(), is);
		assertEquals(connector.getCharset(), UTF_8);
	}
}
