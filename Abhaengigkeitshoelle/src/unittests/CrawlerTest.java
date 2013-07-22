package unittests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import model.http.crawler.Crawler;
import model.http.crawler.ICrawler;
import model.http.crawler.extractorjoint.ExtractLinks;
import model.http.crawler.persistentbuffer.PersistentBuffer;

import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class CrawlerTest {
	private final URL deep1;
	private final URL deep2;
	private final URL deep3;
	private ICrawler crawler;
	private PersistentBuffer persistentBuffer;
	private ExtractorMock factory;
	 
	public CrawlerTest() throws MalformedURLException {
		deep1 = new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/document.py?Gericht=bgh&Art=en&Datum=2002-7&nr=22041&anz=276&pos=0&Frame=4&.pdf");
		deep2 = new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/document.py?Gericht=bgh&Art=en&Datum=2002-7&nr=22048&anz=276&pos=1&Frame=4&.pdf");
		deep3 = new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/document.py?Gericht=bgh&Art=en&Datum=2002-7&nr=22049&anz=276&pos=2&Frame=4&.pdf");
	}
	/**
	 * Checks if only urls fitting the data namespace are returned by {@link Crawler}
	 * and if after the 3 given different urls no more urls are returned.
	 */
	@Test
	public final void testCrawlerCrawlBufferHttpGetStringString() throws MalformedURLException { 
		String crawlNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/.*";
		String dataNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/document\\.py?Gericht=[^&]+&Art=[^&]+&Datum=[^&]+&nr=[^&]+&anz=[^&]+&pos=[^&]+&Frame=[^&]+&\\.pdf";
		
		createCrawler(crawlNamespace,dataNamespace);
		Set<URL> next = crawler.getNext(1);
		for(URL url : next) {
			assertEquals(deep1, url);
		}
		next = crawler.getNext(1);
		for(URL url : next) {
			assertEquals(deep2, url);
		}
		next = crawler.getNext(1);
		for(URL url : next) {
			assertEquals(deep3, url);
		}
		next = crawler.getNext(1);
		for(URL url : next)
			System.err.println("url: " + url.toExternalForm());
		assertEquals(0, next.size());
	}

	/**
	 * Tests if all searched URL's are within the namespace.
	 * @throws MalformedURLException
	 */
	@Test
	public final void testCrawlerCrawlBufferHttpGetStringStringURL() throws MalformedURLException {
		String crawlNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/.*";
		String dataNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/document\\.py?.*\\.pdf";
		
		createCrawler(crawlNamespace,dataNamespace);
		while(true) {
			Set<URL> next = crawler.getNext(1);
			if(next.size() == 0) break;
			for(URL url : next)
				assertTrue(url.toExternalForm().matches(dataNamespace));
			assertTrue(factory.getLastURL().toExternalForm().matches(crawlNamespace));
		}
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	private void createCrawler(String namespace, String dataNamspace) throws MalformedURLException {
		Set<URL> setreturn1 = new HashSet<URL>() {{
			add(new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=Aktuell&Sort=12288"));
			add(new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=Aktuell&Sort=12288&Seite=1"));
			add(new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=2002&Seite=5"));
			add(new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/document.py?Gericht=bgh&Art=en&Datum=2002&Seite=5&nr=24964&pos=155&anz=2753"));
			add(new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=2002-7"));
		}};
		Set<URL> setreturn2 = new HashSet<URL>() {{
			add(deep1);
			add(deep2);
		}};
		Set<URL> setreturn3 = new HashSet<URL>() {{
			add(new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=Aktuell&Sort=12288"));
			add(deep3);
		}};
		Set<URL> end = Collections.emptySet();
		final ExtractLinks extract = mock(ExtractLinks.class);
		when(extract.get()).thenReturn(setreturn1, setreturn2, setreturn3, end);
		factory = new ExtractorMock(extract);
		
		this.persistentBuffer = new PersistentBuffer() {
			Queue<URL> queue = new LinkedBlockingQueue<URL>();
			Set<URL> seen = new HashSet<URL>();
			public URL poll() {
				URL poll = queue.poll();
				seen.add(poll);
				System.err.println("poll " + poll.toExternalForm());
				return poll;
			}
			public void add(URL url) {
				System.err.println("add " + url.toExternalForm());
				if(!queue.contains(url) && !seen.contains(url)) queue.add(url);
			}
			public boolean isEmpty() {
				return queue.isEmpty();
			}
		};
		this.persistentBuffer.add(new URL("http://www.example.org"));
		this.crawler = new Crawler(this.persistentBuffer, factory, namespace, dataNamspace);
	}
}
