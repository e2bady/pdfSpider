package model.http.crawler.extractorjoint;

import java.net.URL;


public interface ExtractorJoint {
	Extractor<URL> create(URL url);
}
