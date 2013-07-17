package controller.factory;

import model.persistence.dao.file.CrawlerDBInit;
import model.persistence.dbconfig.DB;
import model.persistence.dbconfig.DBConnectionData;

public class DbFactory {
	public static DB createDB() {
		return new DB(
				new DBConnectionData(getDBUser(), getDBPass(), getDBType(), getDBHost(), getDBPort(), getDbName()));
	}

	public static boolean exportDB() {
		CrawlerDBInit export = new CrawlerDBInit(createDBLegacy(), getDbName(), "export.db");
		return export.importDB();
	}
	public static boolean importDB() {
		CrawlerDBInit im = new CrawlerDBInit(createDBLegacy(), getDbName(), "export.db");
		return im.importDB();
	}
	private static com.extensions.dbutil.dbcon.DB createDBLegacy() {
		return new com.extensions.dbutil.dbcon.DB(
								new com.extensions.dbutil.dbcon.DBConnectionData(
									getDBUser(), 
									getDBPass(), 
									getDBType(), 
									getDBHost(), 
									getDBPort(), 
									getDbName()));
	}
	private static int getDBPort() {
		return 3306;
	}
	private static String getDBHost() {
		return "localhost";
	}
	private static String getDBType() {
		return "mysql";
	}
	private static String getDBPass() {
		return "asstastic";
	}
	private static String getDBUser() {
		return "dbuser";
	}
	private static String getDbName() {
		return "crawler";
	}
}
