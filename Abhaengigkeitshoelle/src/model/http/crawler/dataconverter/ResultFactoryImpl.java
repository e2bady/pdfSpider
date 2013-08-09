package model.http.crawler.dataconverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultFactoryImpl implements ResultFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(ResultFactoryImpl.class);
	private final String type;
	private final String category;
	private final String titleRegex;
	private final String dateRegex;
	private final DateFormat dateInstance;

	public ResultFactoryImpl(String category, String type) {
		super();
		this.category = category;
		this.type = type;
		this.titleRegex = "([\\d]+ )?[ARs|V ZR|StR|AnwZ (B)|I ZB|StR]+ [\\d\\/]+";
		this.dateRegex = "([\\d]{1,2}\\. [a-zA-Z]+ [\\d]{4}|[\\d]{1,2}\\. [a-zA-Z]+ [\\d]{2})";
		this.dateInstance = DateFormat.getDateInstance(DateFormat.LONG);
	}
	public ResultFactoryImpl(String type, String category, String titleRegex,
			String dateRegex, DateFormat dateInstance) {
		super();
		this.type = type;
		this.category = category;
		this.titleRegex = titleRegex;
		this.dateRegex = dateRegex;
		this.dateInstance = dateInstance;
	}
	@Override
	public Result getResult(String origin ,String content) {
		SubParser<Date> dateparser = new DateParser(content, dateRegex, dateInstance);
		SubParser<String> titleParser = new TitleParser(content, titleRegex);
		Result parser = null;
		try {
			parser = new SimpleResultParser(
					new URL(origin),
					content, type, category, 
					dateparser,
					titleParser);
		} catch (MalformedURLException e) {
			log.error("Could not parse: " + origin);
		}
		return parser;
	}
}
