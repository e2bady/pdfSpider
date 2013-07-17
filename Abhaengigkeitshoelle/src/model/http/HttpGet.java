package model.http;

import java.net.URL;

import org.jsoup.nodes.Document;

public interface HttpGet {
	Document get(URL url);
}
