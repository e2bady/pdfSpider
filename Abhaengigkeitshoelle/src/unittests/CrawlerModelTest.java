package unittests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import model.persistence.dao.CrawlerModel;
import model.persistence.dao.MySqlPersistentBuffer;
import model.persistence.dbconfig.DB;
import model.persistence.dbconfig.DBConnectionData;
import model.persistence.dbconfig.IDB;

import org.junit.Test;

public class CrawlerModelTest {
	@Test
	public final void testIsCrawled() throws MalformedURLException {
		IDB db = new DB(new DBConnectionData("dbuser", "asstastic", "mysql", "localhost", 3306, "crawler"));
		CrawlerModel buffer = new MySqlPersistentBuffer(db);
		URL url = new URL("http://juris.bundesgerichtshof.de/cgi-bin/rechtsprechung/list.py?Gericht=bgh&Art=en&Datum=Aktuell&Sort=12288");
		buffer.add(url, false);
		assertFalse(buffer.isEmpty());
		assertFalse(buffer.isCrawled(url));
		assertTrue(buffer.getNotCrawled().equals(url));
	}

	@Test
	public final void testAdd() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetNotCrawled() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsEmpty() {
		fail("Not yet implemented"); // TODO
	}
}
