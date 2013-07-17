package model.http.inputstreamconverter;

import model.http.urlconnector.URLConnector;

public interface IInputStreamConverterFactory {
	InputStreamConverter create(URLConnector connector);
}