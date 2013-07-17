package model.http.crawler.extractorjoint;

import java.net.URL;

import model.http.HttpGet;

public class ExtractorFactory implements ExtractorJoint {
	private HttpGet http;
	public ExtractorFactory(HttpGet http) {
		this.http = http;
	}
	public Extractor<URL> create(URL url) {
		return new ExtractLinks(http, url);
	}
}
