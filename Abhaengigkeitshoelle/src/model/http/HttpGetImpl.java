package model.http;

import java.net.URL;

import model.http.inputstreamconverter.IInputStreamConverterFactory;
import model.http.urlconnector.IURLConnectionFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpGetImpl implements HttpGet {
	private static final Logger LOG = LoggerFactory.getLogger(HttpGet.class);
	private final IURLConnectionFactory urlConnector;
	private final IInputStreamConverterFactory converterFactory;

	public HttpGetImpl(final IURLConnectionFactory urlConnector, 
			final IInputStreamConverterFactory converterFactory) {
		this.urlConnector = urlConnector;
		this.converterFactory = converterFactory;
	}

	public Document get(final URL url) {
		String convert = converterFactory.create(this.urlConnector.create(url)).convert();
		LOG.debug("retrieved page: " + convert);
		Document parse = Jsoup.parse(convert);
		parse.setBaseUri(url.toExternalForm());
		return parse;
	}
}

