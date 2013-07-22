package model.http.crawler.extractorjoint;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.Lazy;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractLinks extends Lazy implements Extractor<URL> {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(ExtractLinks.class);
	private final Document document;
	private Set<URL> links = null;
	
	public ExtractLinks(Document httpCon) {
		this.document = httpCon;
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
		Elements select = this.document.select("body a");
		for(Element e : select) {
			String absUrl = e.absUrl("href");
			log.error("checking url: " + absUrl);
			try {
				links.add(new URL(absUrl));
			} catch (MalformedURLException e1) {
				log.error("url was not valid: " + absUrl);
				e1.printStackTrace();
			}
		}
		return true;
	}
}
