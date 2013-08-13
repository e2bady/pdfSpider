package unittests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;

import model.http.crawler.dataconverter.Result;
import model.http.crawler.dataconverter.ResultFactory;
import model.http.crawler.dataconverter.ResultFactoryImpl;

import org.junit.Test;

public class SimpleResultParserTest {
	private final String type = "ABC";
	private final String category = "EDF";
	private final String validURL = "http://www.foo.de/bar";
	private final String invalidURL = "www.foo.de/bar";
	private final ResultFactory factory = new ResultFactoryImpl(category);
	
	@Test
	public void testinvalidURL() throws ParseException {
		String date = "25. Juni 2013";
		String title = "2 ARs 130/13";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";
		Result parser = factory.getResult(invalidURL,content, type);
		assertNull(parser);
	}
	
	@Test
	public void testJunLongLong() throws ParseException {
		String date = "25. Juni 2013";
		String title = "2 ARs 130/13";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";
	
		check(validURL, date, content, type, category, title);
	}
	@Test
	public void testJunShortLong() throws ParseException  {
		String date = "2. Juni 2013";
		String title = "V ZR 1/13";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";

		check(validURL, date, content, type, category, title);
	}
	@Test
	public void testJunShortShort() throws ParseException {
		String date = "2. Jun 13";
		String title = "4 StR 380/12";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";

		check(validURL, date, content, type, category, title);
	}
	@Test
	public void testSepLongLong() throws ParseException {
		String date = "21. September 2013";
		String title = "AnwZ (B) 6/12";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";

		check(validURL, date, content, type, category, title);
	}
	@Test
	public void testSepShortLong() throws ParseException {
		String date = "2. September 2013";
		String title = "I ZB 76/12";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";

		check(validURL, date, content, type, category, title);
	}
	@Test
	public void testSepShortShort() throws ParseException {
		String date = "2. September 13";
		String title = "2 StR 206/12";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";

		check(validURL, date, content, type, category, title);
	}
	@Test
	public void testSepShortShortShort() throws ParseException {
		String date = "2. Sep 13";
		String title = "5 StR 647/12";
		String content = " blabla bla bla " + title + " am " + date + " blabla bla bla 1. Januar 2000";

		check(validURL, date, content, type, category, title);
	}
	private void check(String url,String date, String content, String type, String category, String title)
			throws ParseException {
		Result parser = factory.getResult(url,content, type);
		
		assertEquals(category, parser.getCategory());
		assertEquals(type, parser.getType());
		assertEquals(content, parser.getContent());
		assertEquals(DateFormat.getDateInstance(DateFormat.LONG).parse(date), parser.getPublished());
		assertEquals(title, parser.getTitle());
	}
}

