package model.http.crawler;

import java.net.URL;
import java.util.Set;

public interface ICrawler {
	Set<URL> getNext(int count);
}