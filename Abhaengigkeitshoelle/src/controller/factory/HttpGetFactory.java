package controller.factory;

import model.http.HttpGetImpl;
import model.http.inputstreamconverter.InputStreamConverterFactory;
import model.http.urlconnection.ConnectionFactory;
import model.http.urlconnector.URLConnectionFactory;
import model.http.valueextractor.FieldValueExtractor;

public class HttpGetFactory {
	static HttpGetImpl createHttpGet(ConnectionFactory connectionFactory) {
		return new HttpGetImpl(
				new URLConnectionFactory(connectionFactory, new FieldValueExtractor("charset=")),
				new InputStreamConverterFactory());
	}
}
