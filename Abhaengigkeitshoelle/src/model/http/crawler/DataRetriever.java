package model.http.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import model.http.crawler.dataconverter.DataConverter;
import model.http.crawler.dataconverter.Result;
import model.http.crawler.dataconverter.ResultFactory;
import model.persistence.dao.DataWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRetriever implements IDataRetriever {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(DataRetriever.class);
	private ICrawler crawler;
	private Collection<DataWriter> writer;
	private DataConverter converter;
	private ResultFactory resultfactory;
	private String type;

	public DataRetriever(ICrawler crawler, Collection<DataWriter> writer,
			DataConverter converter, ResultFactory resultfactory, String type) {
		this.crawler = crawler;
		this.writer = writer;
		this.converter = converter;
		this.resultfactory = resultfactory;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.http.crawler.IDataRetriever#crawl(int)
	 */
	public void crawl(int iterations) {
		while (iterations > 0) {
			Set<URL> urls = this.crawler.getNext(iterations);
			for (URL url : urls) {
				log.debug("found " + url.toExternalForm());
				try {
					Result result = this.resultfactory.getResult(
							url.toExternalForm(), 
							this.converter.convert(url), 
							this.type);
					for (DataWriter w : this.writer)
						if (w.contains(url)) {
							w.add(url, result);
							iterations--;
						}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
