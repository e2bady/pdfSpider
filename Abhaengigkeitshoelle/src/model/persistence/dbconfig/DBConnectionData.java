package model.persistence.dbconfig;

public class DBConnectionData {
	private final String userName;
	private final String password;
	private final String dbms;
	private final String serverName;
	private final int portNumber;
	private final String dbName;

	public DBConnectionData(String userName, String password, String dbms,
			String serverName, int portNumber, String dbName) {
		super();
		this.userName = userName;
		this.password = password;
		this.dbms = dbms;
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.dbName = dbName;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getDbms() {
		return dbms;
	}
	public String getServerName() {
		return serverName;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public String getDbName() {
		return dbName;
	}
	@Override
	public String toString() {
		return String
				.format("DBConnectionData [getUserName()=%s, getPassword()=%s, getDbms()=%s, getServerName()=%s, getPortNumber()=%s, getDbName()=%s]",
						getUserName(), getPassword(), getDbms(),
						getServerName(), getPortNumber(), getDbName());
	}
}