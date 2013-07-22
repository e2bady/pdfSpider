package controller.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.persistence.dao.file.CrawlerDBExport;
import model.persistence.dao.file.CrawlerDBInit;
import model.persistence.dbconfig.DB;
import model.persistence.dbconfig.DBConnectionData;

public class DbFactory {
	private static final Logger log = (Logger) LoggerFactory.getLogger(DbFactory.class);
	public static DB createDB() {
		log.error("Creating DBConnectionData Object.");
		log.error("Creating DB Object.");
		return new DB(
				new DBConnectionData(getDBUser(), getDBPass(), getDBType(), getDBHost(), getDBPort(), getDbName()));
	}

	public static boolean exportDB() {
		log.error("Exporting DB...");
		CrawlerDBExport export = new CrawlerDBExport(createDBLegacy(), getDbName(), "export.db");
		return export.exportDB();
	}
	public static boolean importDB() {
		log.error("Importing DB...");
		CrawlerDBInit im = new CrawlerDBInit(createDBLegacy(), getDbName(), "export.db");
		return im.importDB();
	}
	private static com.extensions.dbutil.dbcon.DB createDBLegacy() {
		log.error("Creating legacy DBConnectionData Object.");
		log.error("Creating legacy DB Object.");
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
