package eg.edu.alexu.csd.oop.jdbc.cs69.Resultset;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;
import eg.edu.alexu.csd.oop.jdbc.cs69.ResultSetMetaData.DBResultSetMetaDataImp;
import eg.edu.alexu.csd.oop.jdbc.cs69.Statement.DBStatementImp;

public class DBResultsetImp extends DBResultset{
	DBStatementImp statement = null;
	DBResultSetMetaDataImp metaData ;
	ArrayList<ArrayList<Object>> results = null;
	ArrayList<Object> current = null;
	int row = 0;
	String[] columns;
	MyDatabase d;

	public DBResultsetImp (DBStatementImp statement,ArrayList<ArrayList<Object>> results, MyDatabase d) {
		this.statement = statement;
		this.results = results;
	    columns=d.getColumns();
	    this.d = d;
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		// TODO Auto-generated method stub
		this.row = row;
		if (row <= results.size() || row == -1) {
			if(row == -1) {
				current = results.get(results.size() - 1);
			}
			else {
				current = results.get(row - 1);
			}
			return true;
		}
		else if(row <= 0) {
			current = null;
			return false;
		}
		return false;
	}

	@Override
	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		row = -2;
		current = null;
	}

	@Override
	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		row = 0;
		current = null;
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		statement = null;
		results = null;
		current = null;
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < columns.length; i++) {
			if(columns[i].equals(columnLabel)) {
				return i + 1;
			}
		}
		throw new SQLException();
	}

	@Override
	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		if(results.size() == 0) {
			return false;
		}
		else {
			row = 1;
			current = results.get(row - 1);
			return true;
		}
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		if(columnIndex > 0 && columnIndex <= current.size()) {
			return (int) current.get(columnIndex - 1);
		}
		throw new SQLException();
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < columns.length; i++) {
			if(columns[i].equals(columnLabel)) {
				if(current.get(i).equals(null)) {
					return 0;
				}
				return (int) current.get(i);
			}
		}
		throw new SQLException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		metaData= new DBResultSetMetaDataImp(results,d);
		return metaData;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		if(columnIndex > 0 && columnIndex <= current.size()) {
			return current.get(columnIndex - 1);
		}
		throw new SQLException();
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return statement;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		if(columnIndex > 0 && columnIndex <= current.size()) {
			return String.valueOf(current.get(columnIndex - 1));
		}
		throw new SQLException();
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < columns.length; i++) {
			if(columns[i].equals(columnLabel)) {
				if(current.get(i).equals(null)) {
					return null;
				}
				return String.valueOf(current.get(i));
			}
		}
		throw new SQLException();
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		if(row < -1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		if(row == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return statement == null && results == null;
	}

	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		if(row - 1 == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		if(row == results.size()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		if(results.size() == 0) {
			return false;
		}
		else {
			row = results.size();
			current = results.get(row - 1);
			return true;
		}
	}

	@Override
	public boolean next() throws SQLException {
		// TODO Auto-generated method stub
		row = row + 1;
		if(row <= results.size()) {
			current = results.get(row - 1);
			return true;
		}
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		row = row - 1;
		if(row >= 1) {
			current = results.get(row - 1);
			return true;
		}
		return false;
	}
}


