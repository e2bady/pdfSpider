package model.http.crawler.dataconverter;

import java.io.IOException;
import java.net.URL;

public interface DataConverter {
	String convert(URL dataUrl) throws IOException;
}
