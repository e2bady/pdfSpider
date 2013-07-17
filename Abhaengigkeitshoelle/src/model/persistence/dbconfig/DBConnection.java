package model.persistence.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

class DBConnection {
	private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);
	private Connection conn;
	private DBConnectionData connectionData;
	
	public Connection getSQLConnection() {
		return conn;
	}
	
	public DBConnection(DBConnectionData connectionData) {
		this.connectionData = connectionData;
		if(LOG.isDebugEnabled())
			LOG.debug(this.connectionData.toString());
		try {
			getConnection();
		} catch (SQLException e) {
			LOG.error("SQLException caught during startup. Cannot connect to the Database.", e);
		}
	}
	public Statement getStatement() throws SQLException {
		if(this.conn.isClosed())
			this.getConnection();
		return conn.createStatement();
	}
	private void getConnection() throws SQLException {
	    conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.connectionData.getUserName()); //$NON-NLS-1$
	    connectionProps.put("password", this.connectionData.getPassword()); //$NON-NLS-1$

	    if (this.connectionData.getDbms().equals("mysql")) { //$NON-NLS-1$
	    	try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				LOG.error("MySql Driver seems to be missing.", e);
			}
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.connectionData.getDbms() + "://" + //$NON-NLS-1$ //$NON-NLS-2$
	                   this.connectionData.getServerName() +
	                   ":" + this.connectionData.getPortNumber() + "/", //$NON-NLS-1$ //$NON-NLS-2$
	                   connectionProps);
	    } else if (this.connectionData.getDbms().equals("derby")) { //$NON-NLS-1$
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.connectionData.getDbms() + ":" + //$NON-NLS-1$ //$NON-NLS-2$
	                   this.connectionData.getDbName() +
	                   ";create=true", //$NON-NLS-1$
	                   connectionProps);
	    }
	    if(LOG.isDebugEnabled())
	    	LOG.debug("Database connection successfull: " + this.connectionData.toString()); //$NON-NLS-1$
	}
	public PreparedStatement getStatement(String sql) throws SQLException {
		if(this.conn.isClosed())
			this.getConnection();
		return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
}
