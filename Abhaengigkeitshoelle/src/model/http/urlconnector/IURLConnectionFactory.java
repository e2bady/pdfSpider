package model.http.urlconnector;

import java.net.URL;

public interface IURLConnectionFactory {
	URLConnector create(URL url);
}