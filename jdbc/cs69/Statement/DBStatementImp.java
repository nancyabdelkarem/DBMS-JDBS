package eg.edu.alexu.csd.oop.jdbc.cs69.Statement;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;
import eg.edu.alexu.csd.oop.jdbc.cs69.Connection.DBConnectionImp;
import eg.edu.alexu.csd.oop.jdbc.cs69.Resultset.DBResultsetImp;

public class DBStatementImp extends DBStatement{
	int seconds ;
	ArrayList<String> commands ;
	DBConnectionImp connection;
	DBResultsetImp resultSet;
	String databaseName;
	String path;
	final MyDatabase d = new MyDatabase();

	public DBStatementImp(DBConnectionImp connection,String path) {
		this.connection = connection;
		this.path=path;
		final File data=new File (path);
		databaseName=data.getName();
		//databaseName=getName(path);
		System.out.println("path"+databaseName);
		try {
			execute("Create database " + databaseName);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public void addBatch(String sql) throws SQLException {
		if(sql==null || sql.length()==0){
			throw new SQLException("The sql string cannot be empty");
		}
		else{
		commands.add(sql);
		}
	}

	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		try{
		commands.clear();
		}catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws SQLException {
   try{
		connection = null;
		commands = null;
   }catch (final Exception e) {
		e.printStackTrace();
	}
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		if(sql==null || sql.length()==0){
			throw new SQLException();
		}
		Object[][]array;
		if(sql.toLowerCase().contains("select")) {
			try{
			array = d.executeQuery(sql);
			}catch(final Exception e){
				return false;
			}
			if(array.length == 0) {
				return false;
			}
			return true;
		}
		else if(sql.toLowerCase().contains("create") && sql.toLowerCase().contains("database")){
			//d.createDatabase(databaseName, true);
			final File data=new File (path);
			d.theDir=data;
			d.executeStructureQuery(sql);
			return false;
		}
		else if (sql.toLowerCase().contains("create") && sql.toLowerCase().contains("table")){
			d.executeStructureQuery(sql);
			return false;
		}
		else if (sql.toLowerCase().contains("drop")) {
			d.executeStructureQuery(sql);
			return true;
		}
		return false;

	}

	@Override
	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		if(commands.size()==0 || commands==null){
			throw new SQLException();
			}
		final int[] updateCount = new int [commands.size()];
		for(int i = 0; i < commands.size(); i++) {
			updateCount[i] = executeUpdate(commands.get(i));
		}
		return updateCount;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		if(sql==null || sql.length()==0){
			throw new SQLException("The sql string cannot be empty");
		}
		final Object[][] array = d.executeQuery(sql);
		if(array.length==0){
			throw new SQLException();
		}
		final ArrayList<ArrayList<Object>> list1 = new ArrayList<>();
		for(int i = 0; i < array.length; i++) {
			final ArrayList<Object> list2 = new ArrayList<>();
			for (int j = 0; j < array[0].length; j++) {
				list2.add(array[i][j]);
			}
			list1.add(list2);
		}
		resultSet = new DBResultsetImp(this, list1, d);
		return resultSet;
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		if(sql.length()==0 || sql==null){
			throw new SQLException();
			}
		else{
		if (sql.toLowerCase().contains("insert") || sql.toLowerCase().contains("update") || sql.toLowerCase().contains("delete")) {
			return d.executeUpdateQuery(sql);
		}
		}
		return 0;

	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return connection;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return seconds;
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		this.seconds = seconds;
	}
}
