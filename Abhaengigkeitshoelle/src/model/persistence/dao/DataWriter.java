package model.persistence.dao;

import java.net.URL;
import java.util.List;

import model.http.crawler.dataconverter.Result;

public interface DataWriter {
	void add(URL origin, Result data);
	Result get(URL origin);
	List<Result> get(String origin);
	List<Result> getbyType(String type);
	List<URL> ls();
	boolean contains(URL origin);
}