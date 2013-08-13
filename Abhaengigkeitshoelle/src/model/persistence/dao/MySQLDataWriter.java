package model.persistence.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.http.crawler.dataconverter.Result;
import model.http.crawler.dataconverter.ResultFactory;
import model.persistence.dbconfig.IDB;
import model.persistence.jooq.Tables;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.javafx.beans.annotations.NonNull;

public class MySQLDataWriter implements DataWriter {

	private static final Logger log = (Logger) LoggerFactory
			.getLogger(MySQLDataWriter.class);
	private final IDB db;
	private ResultFactory resultFactory;
	
	public MySQLDataWriter(IDB db, ResultFactory resultFactory) {
		this.db = db;
		this.resultFactory = resultFactory;
	}

	public void add(URL origin, Result data) {
		if(!exists(origin)) {
			DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
			dsl.insertInto(Tables.DATA, Tables.DATA.ORIGIN, Tables.DATA.DATA_)
			.values(origin.toExternalForm(), data.getContent()).execute();
		}
	}

	public Result get(@NonNull URL origin) {
		DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		Condition condition = Tables.DATA.ORIGIN.equalIgnoreCase(origin.toExternalForm());
		Record2<String, String> fetchOne = dsl
				.select(Tables.DATA.ORIGIN, Tables.DATA.DATA_)
				.from(Tables.DATA)
				.where(condition)
				.fetchOne();
		return resultFactory.getResult(
				fetchOne != null ? fetchOne.getValue(Tables.DATA.ORIGIN) : "",
				fetchOne != null ? fetchOne.getValue(Tables.DATA.DATA_) : "",
				fetchOne != null ? fetchOne.getValue(Tables.DATA.TYPE) : "");
	}

	public List<URL> ls() {
		List<URL> lst = new LinkedList<URL>();
		DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		for(Record r : (dsl.select(Tables.DATA.ORIGIN).from(Tables.DATA).fetch())) {
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

	@Override
	public boolean contains(URL origin) {
		return this.exists(origin);
	}

	@Override
	public List<Result> get(String origin) {
		Condition condition = Tables.DATA.DATA_.like("%"+origin+"%");
		return getResults(condition);
	}
	@Override
	public List<Result> getbyType(String type) {
		Condition condition = Tables.DATA.TYPE.like(type);
		return getResults(condition);
	}

	private List<Result> getResults(Condition condition) {
		DSLContext dsl = DSL.using(this.db.getConnection(), SQLDialect.MYSQL);
		
		org.jooq.Result<Record3<String, String, String>> fetchOne = dsl.select(Tables.DATA.ORIGIN, Tables.DATA.DATA_, Tables.DATA.TYPE).from(Tables.DATA)
				.where(condition).fetch();
		List<Result> result = new ArrayList<>(fetchOne.size());
		for(Record3<String, String, String> r : fetchOne)
			result.add(resultFactory.getResult(
					fetchOne != null ? r.getValue(Tables.DATA.ORIGIN) : "",
					fetchOne != null ? r.getValue(Tables.DATA.DATA_) : "",
					fetchOne != null ? r.getValue(Tables.DATA.TYPE) : ""));
		return result;
	}
}
