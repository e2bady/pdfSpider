package model.persistence.dbconfig;

import java.sql.Connection;

public interface IDB {
	Connection getConnection();
}
