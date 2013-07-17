package controller.factory;

import java.net.MalformedURLException;


import model.http.crawler.DataRetriever;
import model.http.crawler.ICrawler;
import model.http.crawler.IDataRetriever;
import model.http.crawler.dataconverter.PdfToText;
import model.http.proxy.IProxySetter;
import model.http.urlconnection.ConnectionFactory;
import model.http.urlconnection.HttpURLConnectionConfiguration;
import model.persistence.dao.DataWriter;
import model.persistence.dao.MySQLDataWriter;
import model.persistence.dbconfig.IDB;

public class ModelFactory {
	public static IDataRetriever createMySQLHttpRetriever(IProxySetter proxy, IDB db,
			String crawlNamespace, String dataNamespace, String startAt)
			throws MalformedURLException {
		
		ConnectionFactory connectionFactory = new HttpURLConnectionConfiguration(proxy, "GET", "UTF-8", "de");
		ICrawler crawler = CrawlerFactory.createCrawler(db, crawlNamespace, dataNamespace, startAt, connectionFactory);
		DataWriter writer = new MySQLDataWriter(db);
		IDataRetriever retriever = new DataRetriever(crawler, writer, new PdfToText(connectionFactory));
		return retriever;
	}

	
}
