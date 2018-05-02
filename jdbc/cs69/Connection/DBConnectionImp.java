package eg.edu.alexu.csd.oop.jdbc.cs69.Connection;

import java.sql.SQLException;
import java.sql.Statement;

import eg.edu.alexu.csd.oop.jdbc.cs69.Statement.DBStatementImp;

public class DBConnectionImp extends DBConnection{
	private DBStatementImp statement;
	String path;

	public DBConnectionImp(String url, String path){
		//send to db the dir path and url is the protocol
		this.path = path;
	}
	@Override
	public void close() throws SQLException {
		statement=null;
	}

	@Override
	public Statement createStatement() throws SQLException {


		 statement = new DBStatementImp(this,path);
	        return statement;
	}
}
