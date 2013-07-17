package model.persistence.dao;

import java.net.URL;

public interface CrawlerModel {
	boolean isCrawled(URL url);
	void update(URL url, boolean crawled);
	void add(URL url, boolean crawled);
	URL getNotCrawled();
	boolean isEmpty();
}
