package eg.edu.alexu.csd.oop.db.cs69.Model.Strategy;

import java.sql.SQLException;
import java.util.Map;

public  class Context {
	   private final Strategy strategy;

	   public Context(Strategy strategy){
	      this.strategy = strategy;
	   }

	   public void executeStrategy(String databaseName){
	      strategy.Database(databaseName);
	      return;
	   }
	   public void executeStrategy1(String tableName){
		      strategy.dropTable(tableName);
		      return;
		   }
	   public void executeStrategy2(String tableName,String[] columnNames , String[] type){
		      strategy.createTable(tableName, columnNames, type);
		      return;
		   }
	   public Object[][] executeStrategy3(String[] columns, String tableName, String path){
		   return strategy.selectColumns(columns, tableName, path);

		   }
	   public Object[][] executeStrategy4(String[] columns, String tableName, String condition, String path){
		   return  strategy.selectColumnsCondition(columns, tableName, condition, path);
		   }

	   /*public void executeStrategy5( Map<String, String> invalues,String tableName,String path){

		      strategy.InsertQueryWithCols(invalues, tableName, path);

		      return;
		   }*/
	   public int executeStrategy6( Map<String, String> upvalues, String tableName, String path ,String[] columnNames) throws SQLException{
		   return strategy.UpdateDataBaseWithOutCondition(upvalues, tableName, path, columnNames);

		   }
	   public int executeStrategy7( Map<String, String> upvalues, String condition, String tableName, String path, String []columnNames) throws SQLException{
		     return strategy.UpdateDataBaseWithCondition(upvalues, condition, tableName, path, columnNames);

		   }
	   public int executeStrategy8( String condition, String tableName, String path){
		     return strategy.DeleteDataBaseWhere(condition, tableName, path);

		   }
	   public int executeStrategy9( String tableName, String path){
		     return strategy.DeleteAllDataBase(tableName, path);

		   }

}
