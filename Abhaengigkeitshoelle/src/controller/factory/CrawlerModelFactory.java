package controller.factory;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.http.crawler.persistentbuffer.CrawlBuffer;
import model.http.crawler.persistentbuffer.PersistentBuffer;
import model.persistence.dao.CrawlerModel;
import model.persistence.dao.MySqlPersistentBuffer;
import model.persistence.dbconfig.IDB;

public class CrawlerModelFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(CrawlerModelFactory.class);
	static PersistentBuffer createCrawlerModel(IDB db, String startAt)
			throws MalformedURLException {
		log.error("Creating CrawlerModel Object.");
		CrawlerModel mySqlPersistentBuffer = new MySqlPersistentBuffer(db);
		log.error("Creating PersistentBuffer Object.");
		PersistentBuffer buffer = new CrawlBuffer(mySqlPersistentBuffer);
		log.error("Importing Database into PersistentBuffer.");
		if(!buffer.isEmpty() && DbFactory.importDB() && startAt != null) {
			log.error("Adding startURL: " + startAt);
			buffer.add(new URL(startAt));
		}
		return buffer;
	}
}
