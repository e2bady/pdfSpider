package controller.factory;

import java.net.MalformedURLException;
import java.net.URL;

import model.http.crawler.persistentbuffer.CrawlBuffer;
import model.http.crawler.persistentbuffer.PersistentBuffer;
import model.persistence.dao.CrawlerModel;
import model.persistence.dao.MySqlPersistentBuffer;
import model.persistence.dbconfig.IDB;

public class CrawlerModelFactory {
	static PersistentBuffer createCrawlerModel(IDB db, String startAt)
			throws MalformedURLException {
		CrawlerModel mySqlPersistentBuffer = new MySqlPersistentBuffer(db);
		
		PersistentBuffer buffer = new CrawlBuffer(mySqlPersistentBuffer);
		if(!buffer.isEmpty() && DbFactory.importDB() && startAt != null) {
			buffer.add(new URL(startAt));
		}
		return buffer;
	}
}
