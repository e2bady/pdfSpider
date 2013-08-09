package model.http.crawler.dataconverter;

import java.net.URL;
import java.util.Date;

import model.Lazy;

public class SimpleResultParser extends Lazy implements Result {
	private final URL origin;
	private final String content;
	private Date published;
	private final String category;
	private final String type;
	private final SubParser<Date> dateparser;
	private final SubParser<String> titleParser;
	private String title;
	
	@Override
	public boolean isEmpty() {
		return content.equals("");
	}

	public SimpleResultParser(URL origin,String content, String type, String category, 
			SubParser<Date> dateparser, SubParser<String> titleParser) {
		this.origin = origin;
		this.content = content;
		this.type = type;
		this.category = category;
		this.dateparser = dateparser;
		this.titleParser = titleParser;
	}

	@Override
	public String toString() {
		return "SimpleResultParser [getPublished()=" + getPublished()
				+ ", getTitle()="
				+ getTitle() + ", getType()=" + getType() + ", getCategory()="
				+ getCategory() + "]";
	}

	@Override
	public Date getPublished() {
		this.lazyLoad();
		return this.published;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public String getTitle() {
		this.lazyLoad();
		return this.title;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getCategory() {
		return this.category;
	}
	@Override
	public URL getOrigin() {
		return origin;
	}
	@Override
	protected boolean load() {
		this.published = this.dateparser.parse();
		this.title = this.titleParser.parse();
		return true;
	}
	
}
