package model.http.crawler.extractorjoint;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.Lazy;
import model.http.HttpGet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractLinks extends Lazy implements Extractor<URL> {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(ExtractLinks.class);
	private final URL url;
	private final HttpGet httpcon;
	private Set<URL> links = null;
	
	public ExtractLinks(HttpGet httpCon, URL url) {
		this.url = url;
		this.httpcon = httpCon;
	}
	/* (non-Javadoc)
	 * @see model.http.crawler.Extractor#getLinks()
	 */
	public Set<URL> get() {
		super.lazyLoad();
		return Collections.unmodifiableSet(links);
	}
	@Override
	protected boolean load() {
		if(links != null) return true;
		links = new HashSet<URL>();
		Document doc = this.httpcon.get(this.url);
		log.debug("extracting links from: " + this.url.getProtocol() + "://" + this.url.getHost() + "\n" + doc.toString());
		doc.setBaseUri(this.url.getProtocol() + "://" + this.url.getHost());
		Elements select = doc.select("body a");
		for(Element e : select) {
			String absUrl = e.attr("href");
			log.error("checking url: " + absUrl);
			try {
				URL url = new URL(this.url, absUrl);
				links.add(url);
			} catch (MalformedURLException e1) {
				log.error("url was not valid: " + absUrl);
				e1.printStackTrace();
			}
		}
		return true;
	}
}
