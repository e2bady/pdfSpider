package model.persistence.dbconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is only a wrapper around {@link DBConnection}, to avoid misuse and to be able to 
 * add logic somewhere if the need should ever arise.
 * @author XTFIWS
 *
 */
public class DB implements IDB {
	private final DBConnection connection;
	
	public DB(DBConnectionData connectionData) {
		this.connection = new DBConnection(connectionData);
	}
	
	public Statement getStatement() throws SQLException {
		return connection.getStatement();
	}
	public PreparedStatement getStatement(String sql) throws SQLException {
		return connection.getStatement(sql);
	}
	public Connection getConnection() {
		return connection.getSQLConnection();
	}
}
