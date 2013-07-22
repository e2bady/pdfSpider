package unittests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import model.http.crawler.extractorjoint.ExtractLinks;
import model.http.crawler.extractorjoint.Extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ExtractLinksTest {
	private static final String http = "<html><head></head><body>" +
			"<a href=\"http://www.google.com\">google</a>" +
			"<a href=\"/internallink.htm\">internal</a>" +
			"<a href=\"./relative.htm\">internalrelativ</a>" +
			"</body></html>";
	private static final String http2 = "<html><head></head><body>" +
			"<a href=\"http://www.google.com\">google</a>" +
			"<a href=\"/internallink.htm\">internal</a>" +
			"<a href=\"./relative.htm\">internalrelativ</a>" +
			"<a href=\"relative.htm\">internalrelativ</a>" +
			"</body></html>";
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public final void notnull() {
		Document parse = Jsoup.parse("");
		parse.setBaseUri("http://www.tkse.de/foo/bar.htm");
		Extractor<URL> links = new ExtractLinks(parse);
		assertNotNull(links.get());
	}
	@Test
	public final void testImmutability() throws MalformedURLException {
		Document parse = Jsoup.parse(http);
		parse.setBaseUri("http://www.tkse.de/foo/bar.htm");
		Extractor<URL> links = new ExtractLinks(parse);
		Set<URL> refs = links.get();
		exception.expect(UnsupportedOperationException.class);
		refs.add(new URL("http://www.google.com"));
	}
	@Test
	public final void testgetLinks() throws MalformedURLException {
		Document parse = Jsoup.parse(http);
		parse.setBaseUri("http://www.tkse.de/foo/bar.htm");
		Extractor<URL> links = new ExtractLinks(parse);
		if(links.get().size() != 3) fail("Links weren't parsed correctly.");
	}
	@Test
	public final void testBaseURI() throws MalformedURLException {
		@SuppressWarnings({ "serial" })
		Set<URL> expected = new HashSet<URL>() {{
			this.add(new URL("http://www.google.com"));
			this.add(new URL("http://www.example.com/internallink.htm"));
			this.add(new URL("http://www.example.com/bar/relative.htm"));
			}};
		Document parse = Jsoup.parse(http2);
		parse.setBaseUri("http://www.example.com/bar/somepage.htm");
		Extractor<URL> links = new ExtractLinks(Jsoup.parse(http2));
		Set<URL> refs = links.get();
		for(URL link : refs) {
			if(!expected.contains(link)) fail("link gotten was not found within expected.");
		}
	}
}
