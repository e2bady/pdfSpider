package model.http.urlconnection;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public interface ConnectionFactory {
	URLConnection establishConnection(URL url) throws IOException, ProtocolException;
	String getAgent();
}