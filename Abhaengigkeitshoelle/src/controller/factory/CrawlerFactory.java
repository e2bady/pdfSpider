package controller.factory;

import java.net.MalformedURLException;

import model.http.HttpGet;
import model.http.crawler.Crawler;
import model.http.crawler.ICrawler;
import model.http.crawler.extractorjoint.ExtractorFactory;
import model.http.crawler.persistentbuffer.PersistentBuffer;
import model.http.urlconnection.ConnectionFactory;
import model.persistence.dbconfig.IDB;

public class CrawlerFactory {
	static ICrawler createCrawler(IDB db, String crawlNamespace,
			String dataNamespace, String startAt,
			ConnectionFactory connectionFactory) throws MalformedURLException {
		HttpGet http = HttpGetFactory.createHttpGet(connectionFactory);
		
		ExtractorFactory factory2 = new ExtractorFactory(http);
		
		PersistentBuffer buffer = CrawlerModelFactory.createCrawlerModel(db, startAt);
		ICrawler crawler = new Crawler(buffer, factory2, crawlNamespace, dataNamespace);
		return crawler;
	}
}
