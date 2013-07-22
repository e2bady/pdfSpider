package model.http.crawler.extractorjoint;

import java.net.URL;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.HttpGet;

public class ExtractorFactory implements ExtractorJoint {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(ExtractorFactory.class);
	private HttpGet http;
	public ExtractorFactory(HttpGet http) {
		this.http = http;
	}
	public Extractor<URL> create(final URL url) {
		Document doc = http.get(url);
		log.debug("extracting links from: " + url.getProtocol() + "://" + url.getHost() + "\n" + doc.toString());
		doc.setBaseUri(url.toExternalForm());
		return new ExtractLinks(doc);
	}
}
