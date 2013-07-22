package unittests;

import java.net.URL;

import model.http.HttpGet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MockHttpGet implements HttpGet {
	private int mode = 0;
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Document get(URL url) {
		if(mode == 0) {
			Document doc = Jsoup.parse("<html><head></head><body>" +
					"<a href=\"http://www.google.com\">google</a>" +
					"<a href=\"/internallink.htm\">internal</a>" +
					"<a href=\"./relative.htm\">internalrelativ</a>" +
					"</body></html>");
			doc.setBaseUri(url.toExternalForm());
			return doc;
		} else {
			Document doc = Jsoup.parse("<html><head></head><body>" +
					"</body></html>");
			doc.setBaseUri(url.toExternalForm());
			return doc;
		}
	}
}
