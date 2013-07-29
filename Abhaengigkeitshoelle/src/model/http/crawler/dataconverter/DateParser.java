package model.http.crawler.dataconverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Lazy;

public class DateParser extends Lazy implements SubParser<Date> {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(DateParser.class);
	private Date published;
	private final String content;
	private final String regex;
	private final DateFormat df;

	public DateParser(String content, String regex, DateFormat df) {
		this.content = content;
		this.regex = regex;
		this.df = df;
	}
	@Override
	public Date parse() {
		this.lazyLoad();
		return published;
	}
	@Override
	protected boolean load() {
		Pattern ptrn = Pattern.compile(regex);
		Matcher mtchr = ptrn.matcher(this.content);
		loadwhile: while(mtchr.find()) {
		    String match = mtchr.group();
		    if(log.isDebugEnabled())
		    	log.debug("Match = <" + match + ">");
		    try {
				this.published = df.parse(match);
				break loadwhile;
			} catch (ParseException e) {
				this.published = null;
				continue loadwhile;
			}
		}
		return true;
	}
}
