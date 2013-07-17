package model.http.urlconnector;

import java.io.InputStream;

public interface URLConnector {
	InputStream getResponse();
	String getCharset();
}