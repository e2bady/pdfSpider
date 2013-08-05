package model.http.crawler.persistentbuffer;

import java.net.URL;

public interface PersistentBuffer {
	void add(URL url);
	void add(URL url, boolean crawled);
	URL poll();
	boolean isEmpty();
}