package model.persistence.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import model.persistence.dbconfig.IDB;
import model.persistence.jooq.Tables;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLDataWriter implements DataWriter {

	private static final Logger log = (Logger) LoggerFactory
			.getLogger(MySQLDataWriter.class);
	private final IDB db;
	
	public MySQLDataWriter(IDB db) {
		this.db = db;
	}

	public void add(URL origin, String data) {
		if(!exists(origin)) {
			DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
			dsl.insertInto(Tables.DATA, Tables.DATA.ORIGIN, Tables.DATA.DATA_)
			.values(origin.toExternalForm(), data).execute();
		}
	}

	public String get(URL origin) {
		DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		return (dsl.select(Tables.DATA.DATA_).fetchOne()).getValue(Tables.DATA.DATA_);
	}

	public List<URL> ls() {
		List<URL> lst = new LinkedList<URL>();
		DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		for(Record r : (dsl.select(Tables.DATA.ORIGIN).fetch())) {
			String origin = r.getValue(Tables.DATA.ORIGIN);
			try {
				lst.add(new URL(origin));
			} catch (MalformedURLException e) {
				log.error("Could not parse: " + origin);
			}
		}
		return lst;
	}
	private boolean exists(URL origin) {
		return (DSL.using(this.db.getConnection(), SQLDialect.MYSQL)
				.select(Tables.DATA.IDDATA).from(Tables.DATA)
				.where(Tables.DATA.ORIGIN.equal(origin.toExternalForm()))
				.fetchOne()) != null;
	}
}
