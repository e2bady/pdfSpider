package unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import model.http.HttpGet;
import model.http.HttpGetImpl;

import model.http.inputstreamconverter.IInputStreamConverterFactory;
import model.http.inputstreamconverter.InputStreamConverter;
import model.http.urlconnector.IURLConnectionFactory;
import model.http.urlconnector.URLConnector;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.mockito.Mockito;

public class HttpGetImplTest {
	@Test
	public final void testHttpGetImpl() throws IOException {
		IURLConnectionFactory mockfactory = Mockito.mock(IURLConnectionFactory.class);
		IInputStreamConverterFactory mockConverterFactory = Mockito.mock(IInputStreamConverterFactory.class);
		InputStreamConverter mockStreamConverter = Mockito.mock(InputStreamConverter.class);
		Mockito.when(mockConverterFactory.create(Mockito.any(URLConnector.class))).thenReturn(mockStreamConverter);
		Mockito.when(mockStreamConverter.convert()).thenReturn("<html><head></head><body>" +
				"<a href=\"http://www.google.com\">google</a>" +
				"<a href=\"/internallink.htm\">internal</a>" +
				"<a href=\"./relative.htm\">internalrelativ</a>" +
				"</body></html>");
		
		HttpGet getter = new HttpGetImpl(mockfactory, mockConverterFactory);
		Document doc = getter.get(new URL("http://www.google.com"));
		System.err.println(doc.toString());
		assertEquals("Document did not include exactly 3 links. ",3, doc.select("a").size());
		assertEquals("Document did not include exactly 1 head. ",1, doc.select("head").size());
		assertEquals("Document did not include exactly 1 body. ",1, doc.select("body").size());
	}
}
