package model.http.crawler.dataconverter;

import java.util.Date;

import model.Lazy;

public class SimpleResultParser extends Lazy implements Result {
	private String content;
	private Date published;
	private String category;
	private String type;
	private SubParser<Date> dateparser;
	private SubParser<String> titleParser;
	private String title;

	public SimpleResultParser(String content, String type, String category, 
			SubParser<Date> dateparser, SubParser<String> titleParser) {
		this.content = content;
		this.type = type;
		this.category = category;
		this.dateparser = dateparser;
		this.titleParser = titleParser;
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
	protected boolean load() {
		this.published = this.dateparser.parse();
		this.title = this.titleParser.parse();
		return true;
	}
}
