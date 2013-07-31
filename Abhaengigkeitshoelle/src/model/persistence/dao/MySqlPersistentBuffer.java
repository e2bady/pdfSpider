package model.persistence.dao;

import java.net.MalformedURLException;
import java.net.URL;

import model.persistence.dbconfig.IDB;
import model.persistence.jooq.Tables;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.InvalidResultException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlPersistentBuffer implements CrawlerModel {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(MySqlPersistentBuffer.class);
	private IDB db;

	public MySqlPersistentBuffer(IDB db) {
		this.db = db;
	}
	
	public boolean isCrawled(URL url) {
		DSLContext iscrawled = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		return iscrawled.select().from(Tables.CRAWLED)
		.where(
				Tables.CRAWLED.URL.equal(url.toExternalForm()),
				Tables.CRAWLED.CRAWLED_.equal(1)
				).fetch().size() > 0;
	}
	
	public boolean exists(URL url) {
		DSLContext iscrawled = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		return iscrawled.select().from(Tables.CRAWLED)
		.where(
				Tables.CRAWLED.URL.equal(url.toExternalForm())
				).fetch().size() > 0;
	}

	public void add(URL url, boolean crawled) {
		if(this.exists(url)) this.update(url, crawled);
		else {
			addwoCheck(url, crawled);
		}
	}

	private void addwoCheck(URL url, boolean crawled) {
		DSLContext iscrawled = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		iscrawled.insertInto(Tables.CRAWLED, 
			Tables.CRAWLED.URL, Tables.CRAWLED.CRAWLED_)
			.values(url.toExternalForm(), boolean2Int(crawled)).execute();
	}

	private int boolean2Int(boolean crawled) {
		return crawled ? 1 : 0;
	}
	
	public void update(URL url, boolean crawled) {
		if(!this.exists(url)) addwoCheck(url, crawled);
		else {
			if(crawled == true) {
				log.error("Updated " + url.toExternalForm());
			}
			DSLContext iscrawled = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
			iscrawled.update(Tables.CRAWLED)
					 .set(Tables.CRAWLED.CRAWLED_, boolean2Int(crawled))
					 .where(Tables.CRAWLED.URL.equal(url.toExternalForm())).execute();
		}
	}

	public URL getNotCrawled() {
		DSLContext iscrawled = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		try {
			return new URL((iscrawled.select(Tables.CRAWLED.URL).from(Tables.CRAWLED)
					.where(
							Tables.CRAWLED.CRAWLED_.equal(0)
							).fetch()).iterator().next().value1());
		} catch (InvalidResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean isEmpty() {
		return !(DSL.using(this.db.getConnection(), SQLDialect.MYSQL)
				.select(Tables.CRAWLED.IDCRAWLED).from(Tables.CRAWLED)
				.fetch().size() > 0);
	}

	@Override
	public boolean contains(URL next) {
		return this.exists(next);
	}
}
