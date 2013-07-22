package controller.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.HttpGetImpl;
import model.http.inputstreamconverter.InputStreamConverterFactory;
import model.http.urlconnection.ConnectionFactory;
import model.http.urlconnector.URLConnectionFactory;
import model.http.valueextractor.FieldValueExtractor;

public class HttpGetFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(HttpGetFactory.class);
	static HttpGetImpl createHttpGet(ConnectionFactory connectionFactory) {
		log.error("Creating URLConnectionFactory Object.");
		log.error("Creating FieldValueExtractor Object.");
		log.error("Creating InputStreamConverterFactory Object.");
		log.error("Creating HttpGetImpl Object.");
		return new HttpGetImpl(
				new URLConnectionFactory(connectionFactory, new FieldValueExtractor("charset=")),
				new InputStreamConverterFactory());
	}
}
