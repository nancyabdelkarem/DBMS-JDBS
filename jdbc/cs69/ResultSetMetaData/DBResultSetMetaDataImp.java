package eg.edu.alexu.csd.oop.jdbc.cs69.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;


public class DBResultSetMetaDataImp extends DBResultSetMetaData{
	ArrayList<ArrayList<Object>> results;
	String[] columns;
	String[] types;
    String tableName;

    public  DBResultSetMetaDataImp(ArrayList<ArrayList<Object>> results, MyDatabase d){
		this.results=results;
	    columns=d.getColumns();
	    types=d.getType();
	    this.tableName=d.getTableName();
	}



	@Override
	public int getColumnCount() throws SQLException {
          if(results.size() != 0){
		return results.get(0).size();
       }else{
             throw new SQLException ();
      }
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {

		if(column > 0 && column <= results.get(0).size()){
			return columns[column-1];
			}
			else{
				throw new SQLException();
			}
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		if(column > 0 && column <= results.get(0).size()){
		return columns[column-1];
		}
		else{
			throw new SQLException();
		}
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		if(column > 0 && column <= results.get(0).size()){
			if(  types[column-1] == "INT"){
				return Types.INTEGER;
			}
			else if(types[column-1] == "VARCHAR"){
				return Types.VARCHAR;
			}

			else{
				return Types.OTHER;
			}

		}
		throw new SQLException();

	}

	@Override
	public String getTableName(int column) throws SQLException {
		if(column > 0 && column <= results.get(0).size()){
			return tableName;
		}
		else{
			throw new SQLException();
		}
	}

}
