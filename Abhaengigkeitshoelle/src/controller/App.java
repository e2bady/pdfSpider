package controller;

import java.net.MalformedURLException;

import model.http.crawler.IDataRetriever;
import model.http.crawler.dataconverter.ResultFactoryImpl;
import model.http.proxy.IProxySetter;
import model.persistence.dbconfig.IDB;
import controller.factory.DbFactory;
import controller.factory.ModelFactory;
import controller.factory.ProxyFactory;

public class App {
	/**
	 * @throws MalformedURLException 
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args) throws MalformedURLException {
		String crawlNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/.*";
		String dataNamespace = "http://juris\\.bundesgerichtshof\\.de/cgi-bin/rechtsprechung/document\\.py?.*\\.pdf";
		String startAt = "http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=Aktuell&Sort=12288";
		String titleRegex = "([\\d]+ )?[ARs|V ZR|StR|AnwZ (B)|I ZB|StR]+ [\\d\\/]+";
		String dateRegex = "([\\d]{1,2}\\. [a-zA-Z]+ [\\d]{4}|[\\d]{1,2}\\. [a-zA-Z]+ [\\d]{2})";
		String category = "Recht";
		String type = "BGH";
		
		IProxySetter proxy = ProxyFactory.createProxy();
		IDB db = DbFactory.createDB();
		
		IDataRetriever retriever = ModelFactory
				.createMySQLHttpRetriever(proxy, db, 
				crawlNamespace, dataNamespace, startAt, 
				new ResultFactoryImpl(category, titleRegex, dateRegex), type);
		try {
			retriever.crawl(100);
		} finally {
			DbFactory.exportDB();
		}
	}
}
