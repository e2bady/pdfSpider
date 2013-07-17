package model.http.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import model.http.crawler.dataconverter.DataConverter;
import model.persistence.dao.DataWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRetriever implements IDataRetriever {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(DataRetriever.class);
	private ICrawler crawler;
	private DataWriter writer;
	private DataConverter converter;
	
	public DataRetriever(ICrawler crawler, DataWriter writer, DataConverter converter) {
		this.crawler = crawler;
		this.writer = writer;
		this.converter = converter;
	}
	/* (non-Javadoc)
	 * @see model.http.crawler.IDataRetriever#crawl(int)
	 */
	public void crawl(int iterations) {
		Set<URL> urls = this.crawler.getNext(iterations);
		for(URL url : urls) {
			log.debug("found " + url.toExternalForm());
			try {
				this.writer.add(url, this.converter.convert(url));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
