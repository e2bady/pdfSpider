package unittests;

import java.net.URL;

import model.http.crawler.extractorjoint.Extractor;
import model.http.crawler.extractorjoint.ExtractorJoint;

public class ExtractorMock implements ExtractorJoint {
	private URL last = null;
	private Extractor<URL> constant;
	
	public ExtractorMock(Extractor<URL> constant) {
		this.constant = constant;
	}
	
	public URL getLastURL() {
		return last;
	}
	public Extractor<URL> create(URL url) {
		this.last = url;
		return this.constant;
	}
}
