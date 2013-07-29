package controller.factory;

import java.net.MalformedURLException;




import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.crawler.DataRetriever;
import model.http.crawler.ICrawler;
import model.http.crawler.IDataRetriever;
import model.http.crawler.dataconverter.PdfToText;
import model.http.crawler.dataconverter.ResultFactory;
import model.http.proxy.IProxySetter;
import model.http.urlconnection.ConnectionFactory;
import model.http.urlconnection.HttpURLConnectionConfiguration;
import model.persistence.dao.DataWriter;
import model.persistence.dao.MySQLDataWriter;
import model.persistence.dbconfig.IDB;

public class ModelFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(ModelFactory.class);
	public static IDataRetriever createMySQLHttpRetriever(IProxySetter proxy, IDB db,
			String crawlNamespace, String dataNamespace, String startAt, ResultFactory resultfactory)
			throws MalformedURLException {
		log.error("Creating HttpURLConnectionConfiguration Object.");
		ConnectionFactory connectionFactory = new HttpURLConnectionConfiguration(proxy, "GET", "UTF-8", "de");
		log.error("Creating ICrawler Object.");
		ICrawler crawler = CrawlerFactory.createCrawler(db, crawlNamespace, dataNamespace, startAt, connectionFactory);
		log.error("Creating DataWriter Object.");
		DataWriter writer = new MySQLDataWriter(db, resultfactory);
		List<DataWriter> lst = new LinkedList<>();
		lst.add(writer);
		log.error("Creating DataRetriever Object.");
		IDataRetriever retriever = new DataRetriever(crawler, lst, new PdfToText(connectionFactory), resultfactory);
		return retriever;
	}

	
}
