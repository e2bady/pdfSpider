package model.persistence.dao.file;

import com.extensions.dbutil.DBFactory;
import com.extensions.dbutil.Export;
import com.extensions.dbutil.dbcon.IDB;

public class CrawlerDBExport extends CrawlerDBToFile {
	public CrawlerDBExport(IDB db, String dbName, String importFrom) {
		super(db, dbName, importFrom);
	}
	public boolean exportDB() {
		Export dbe =  DBFactory.createMySQLExport(this.getDb(), this.getName());
		String mySQLExport = DBFactory.MySQLExport().mySQLExport(this.getName(),dbe);
		return new WriteFile(this.getImportFrom(), mySQLExport).write();
	}
}
