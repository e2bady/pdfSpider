package unittests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import model.http.crawler.extractorjoint.ExtractLinks;
import model.http.crawler.extractorjoint.Extractor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
public class ExtractLinksTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public final void testImmutability() throws MalformedURLException {
		Extractor<URL> links = setUpExtractLinks();
		Set<URL> refs = links.get();
		exception.expect(UnsupportedOperationException.class);
		refs.add(new URL("http://www.google.com"));
	}
	@Test
	public final void testExtract() throws MalformedURLException {
		Extractor<URL> links = setUpExtractLinks();
		exception.expect(MalformedURLException.class);
		URL url = new URL("www.tks.de/page/index.html");
		links = new ExtractLinks(new MockHttpGet().get(url));
		links.get();
	}
	@Test
	public final void testgetLinks() throws MalformedURLException {
		Extractor<URL> links = setUpExtractLinks();
		if(links.get().size() != 3) fail("Links weren't parsed correctly.");
	}
	private Extractor<URL> setUpExtractLinks() throws MalformedURLException {
		MockHttpGet mockHttpGet = new MockHttpGet();
		URL url = new URL("http://www.tks.de/page/index.html");
		Extractor<URL> links = new ExtractLinks(mockHttpGet.get(url));
		links.get();
		return links;
	}
	@Test
	public final void testReanalysation() throws MalformedURLException {
		MockHttpGet mockHttpGet = new MockHttpGet();
		URL url = new URL("http://www.tks.de/page/index.html");
		Extractor<URL> links = new ExtractLinks(mockHttpGet.get(url));
		Set<URL> refs = links.get();
		mockHttpGet.setMode(1);
		Set<URL> refsReanalyse = links.get();
		for(URL link : refsReanalyse) {
			if(!refs.contains(link)) fail("ExtractLinks reanalysed content!!");
		}
	}
	
	@Test
	public final void testBaseURI() throws MalformedURLException {
		Extractor<URL> links = setUpExtractLinks();
		Set<URL> refs = links.get();
		for(URL link : refs)
			System.err.println(link);
	}
}
