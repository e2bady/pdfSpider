package model.persistence.dao.file;

import com.extensions.dbutil.dbcon.IDB;

public abstract class CrawlerDBToFile {
	private final IDB db;
	private final String name;
	private final String importFrom;
	public CrawlerDBToFile(IDB db, String dbName, String importFrom) {
		this.db = db;
		this.name = dbName;
		this.importFrom = importFrom;
	}
	protected IDB getDb() {
		return db;
	}
	protected String getName() {
		return name;
	}
	protected String getImportFrom() {
		return importFrom;
	}
}
