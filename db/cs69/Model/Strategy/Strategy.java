package eg.edu.alexu.csd.oop.db.cs69.Model.Strategy;

import java.sql.SQLException;
import java.util.Map;

public  interface Strategy {
	public void Database(String databaseName);
	public  void dropTable(String tableName);
	public  void createTable(String tableName, String[] columnNames , String[] type);
	public Object[][] selectColumnsCondition(String[] columns, String tableName, String condition, String path);
	public Object[][] selectColumns(String[] columns, String tableName, String path);
	//public  void InsertQueryWithCols(Map<String, String> invalues,String tableName,String path);
	public int UpdateDataBaseWithOutCondition(Map<String, String> upvalues, String tableName, String path ,String[] columnNames) throws SQLException;
	public int UpdateDataBaseWithCondition(Map<String, String> upvalues, String condition, String tableName, String path, String []columnNames) throws SQLException;
	public int DeleteDataBaseWhere(String condition, String tableName, String path);
	public int DeleteAllDataBase(String tableName, String path);
}
