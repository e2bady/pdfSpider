package model.http.crawler.persistentbuffer;

import java.net.URL;

public interface PersistentBuffer {
	void add(URL url);
	URL poll();
	boolean isEmpty();
}