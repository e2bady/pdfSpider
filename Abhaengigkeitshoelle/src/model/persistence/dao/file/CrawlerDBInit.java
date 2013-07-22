package model.persistence.dao.file;

import com.extensions.dbutil.SQLImport;
import com.extensions.dbutil.batchexecutor.DBASqlBatchImpl;
import com.extensions.dbutil.dbcon.IDB;

public class CrawlerDBInit extends CrawlerDBToFile {
	public CrawlerDBInit(IDB db, String dbName, String importFrom) {
		super(db, dbName, importFrom);
	}
	public boolean importDB() {
		String content = new ReadFile(super.getImportFrom()).getContent();
		if(content != null)
			return new SQLImport(new DBASqlBatchImpl(super.getDb())).importSQL(content);
		return false;
	}
}
