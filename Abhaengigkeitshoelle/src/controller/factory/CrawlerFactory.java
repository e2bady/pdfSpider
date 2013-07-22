package controller.factory;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.HttpGet;
import model.http.crawler.Crawler;
import model.http.crawler.ICrawler;
import model.http.crawler.extractorjoint.ExtractorFactory;
import model.http.crawler.persistentbuffer.PersistentBuffer;
import model.http.urlconnection.ConnectionFactory;
import model.persistence.dbconfig.IDB;

public class CrawlerFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(CrawlerFactory.class);
	static ICrawler createCrawler(IDB db, String crawlNamespace,
			String dataNamespace, String startAt,
			ConnectionFactory connectionFactory) throws MalformedURLException {
		log.error("Creating HttpGet Object.");
		HttpGet http = HttpGetFactory.createHttpGet(connectionFactory);
		log.error("Creating ExtractorFactory Object.");
		ExtractorFactory factory2 = new ExtractorFactory(http);
		log.error("Creating PersistentBuffer Object.");
		PersistentBuffer buffer = CrawlerModelFactory.createCrawlerModel(db, startAt);
		log.error("Creating ICrawler Object.");
		ICrawler crawler = new Crawler(buffer, factory2, crawlNamespace, dataNamespace);
		return crawler;
	}
}
