package model.http.crawler.dataconverter;

import java.net.URL;
import java.util.Date;

public interface Result {
	Date getPublished();
	String getContent();
	String getTitle();
	String getType();
	String getCategory();
	boolean isEmpty();
	URL getOrigin();
}
