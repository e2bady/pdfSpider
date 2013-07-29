package model.http.crawler.dataconverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Lazy;

public class TitleParser extends Lazy implements SubParser<String> {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(TitleParser.class);
	private final String content;
	private final String regex;
	private String title;
	
	public TitleParser(String content, String regex) {
		this.content = content;
		this.regex = regex;
	}
	@Override
	public String parse() {
		super.lazyLoad();
		return this.title;
	}
	@Override
	protected boolean load() {
		Pattern ptrn = Pattern.compile(regex);
		Matcher mtchr = ptrn.matcher(this.content);
		loadwhile: while(mtchr.find()) {
		    String match = mtchr.group();
		    if(log.isDebugEnabled())
		    	log.debug("Match = <" + match + ">");
			this.title = match.trim();
			break loadwhile;
		}
		return true;
	}
}
