package model.persistence.dao;

import java.net.URL;
import java.util.List;

public interface DataWriter {
	void add(URL origin, String data);
	String get(URL origin);
	List<URL> ls();
}