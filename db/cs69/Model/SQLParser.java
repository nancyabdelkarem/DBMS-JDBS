package eg.edu.alexu.csd.oop.db.cs69.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Create;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Drop;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Insert;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Select;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Update;
import eg.edu.alexu.csd.oop.db.cs69.Model.Strategy.Context;

public class SQLParser {
	static String [] columnNames;
  static String[] selectColumns;
 String [] type;
 String tableName;
public String [] getColumns() {
	return selectColumns;
}
public String [] getType() {
	return type;
}
public String getTableName() {
	return tableName;
}
public String[] getColsNames(){
	return columnNames;
}
public String[] getSelectedCols(){
	return selectColumns;
}
	public void createDatabaseParser (String query) {
		query = query.replace("CREATE", "");
		query = query.replace("DATABASE", "");
		final String databaseName = query.trim();
		 final Context context = new Context( new Create());
		 context.executeStrategy(databaseName);
	// Create.createDatabase(databaseName);
	}

	public void dropDatabaseParser (String query) {
		query = query.replace("DROP", "");
		query = query.replace("DATABASE", "");
		final String databaseName = query.trim();
		final Context context = new Context(new Drop());
		 context.executeStrategy(databaseName);
	// Drop.dropDatabase(databaseName);
	}

	public void createTableParser (String query) throws SQLException {
		if(!query.contains(")")){
			throw new SQLException();
		}

		query = query.replace("CREATE", "");
		query = query.replace("TABLE ", "");
		final String[] split = query.split("\\(");
		 tableName = split[0].trim();
		final String string = split[1].replace(")", "");
		final String[] split2 = string.split(",");
		if (split2.length == 0) {
			try {
				throw new SQLException();
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		columnNames = new String[split2.length];
		final String[] type = new String[split2.length];
		int i = 0;
		for (final String str : split2) {
			final String[] split3 = str.trim().split(" ");
			columnNames[i] = split3[0];
			type[i] = split3[1];
			i++;
		}
		final Context context = new Context(new Create());
		 context.executeStrategy2(tableName,columnNames,type);
		//Create.createTable(databaseName,columnNames,type);

	}


	public void dropTableParser (String query) {
		query = query.replace("DROP", "");
		query = query.replace("TABLE ", "");
		final String databaseName = query.trim();
		final Context context = new Context(new Drop());
		 context.executeStrategy1(databaseName);

		// Drop.dropTable(databaseName);
	}

	public Object[][] selectParser (String query , String path) {
		query = query.replace("SELECT ", "");
		final String[] split = query.split("FROM ");
		final String[] columns = split[0].split(",");
		int i = 0;
		for (final String str : columns) {
			columns[i] = str.trim();
			i++;
		}
		final String tableName = split[1].trim();
		selectColumns = columns;
		final Context context = new Context(new Select());
		return context.executeStrategy3(columns, tableName, path);
		// s.selectColumns(columns, tableName, path);
	}

	public Object[][]  selectAllParser (String query, String path) {
			 //s.selectColumns(columnNames, tableName, path);

		query = query.replace("SELECT", "");
		query = query.replace("*", "");
		query = query.replace("FROM ", "");
		final String tableName = query.trim();
		selectColumns = columnNames;
		final Context context = new Context(new Select());
		return context.executeStrategy3(columnNames, tableName, path);

}

	public Object[][] selectWhereParser (String query, String path) {

	 //s.selectColumnsCondition(columns, tableName, condition, path);
		query = query.replace("SELECT ", "");
		final String[] split = query.split("FROM ");
		final String[] split2 = split[1].split("WHERE ");
		final String[] columns = split[0].split(",");
		int i = 0;
		for (final String str : columns) {
			columns[i] = str.trim();
			i++;
		}
		final String tableName = split2[0].trim();
		final String condition = split2[1].trim();
		selectColumns = columns;
		final Context context = new Context(new Select());
		return context.executeStrategy4(columns, tableName, condition, path);

	}

	public Object[][]  selectAllWhereParser (String query, String path) {

     // s.selectColumnsCondition(columnNames, tableName, condition, path);


		query = query.replace("SELECT", "");
		query = query.replace("*", "");
		query = query.replace("FROM ", "");
		final String[] split = query.split("WHERE ");
		final String tableName = split[0].trim();
		final String condition = split[1].trim();
		selectColumns = columnNames;
		final Context context = new Context(new Select());
		 return context.executeStrategy4(columnNames, tableName, condition, path);

	}

	public static final boolean InsertQueryWithCols(String sql ,String tableName,String path) throws SQLException {
		Pattern pattern;
		pattern = Pattern.compile("[^(]*\\(([^)]*)\\).*");
		final Matcher m = pattern.matcher(sql);
		m.matches();
		final String columList = m.group(1);
		final String[] columns = columList.trim().split("\\s*,\\s*");

		// replace the , chars inside of strings by the REPLACEMENT_CHAR
		final char[] rest = sql.substring(sql.indexOf("VALUES")).toCharArray();
		boolean inString = false;
		int start = -1;
		int end = -1;
		for (int i = 0; i < rest.length; i++) {
			final char c = rest[i];
			if (c == '\'') {
				rest[i] = ' ';
				inString = !inString;
			} else if (!inString && c == '(') {
				if (start != -1) {
					throw new SQLException("Start brace found twice!");
				}

				start = i + 1;
			} else if (!inString && c == ')') {
				end = i - 1;
				break;
			}
		}

		if (start == -1 || end == -1) {
			throw new SQLException("Start or end of the values part not found!");
		}

		// split the result for having the values
		final String[] values = new String(rest, start, end - start + 1).split("\\s*,\\s*");
		if (values.length != columns.length) {
			throw new SQLException("The number of values differs of the number of columns");
		}

		// collect the results
		final Map<String, String> map = new HashMap<String, String>();
		final ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(columns));
		columnNames=columns;
		final ArrayList<String> list2 = new ArrayList<String>(Arrays.asList(columnNames));
		for (final String item : list2) {
			if (!list1.contains(item)) {
				map.put(item, "null");
			}
		}
		for (int i = 0; i < values.length; i++) {
			map.put(columns[i].trim(), values[i].trim());
		}
		final Insert in = new Insert();
		final boolean x = in.InsertInDataBaseWithCols(map, tableName, path);
		return x;

	}

	public static final boolean InsertQueryWithoutCols(String sql ,String tableName, String path) throws SQLException {
		Pattern pattern;
		pattern = Pattern.compile("[^(]*\\(([^)]*)\\).*");
		final Matcher m = pattern.matcher(sql);
		m.matches();
		final String columList = m.group(1);
		final String[] columns = columList.trim().split("\\s*,\\s*");
		final Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < columns.length; i++) {
			columns[i] = columns[i].replace("'", "");
			map.put(columnNames[i], columns[i].trim());
		}
		if (columns.length != columnNames.length) {
			for (int j = columns.length; j < columnNames.length; j++) {
				map.put(columnNames[j], "null");
			}
		}
		final Insert in = new Insert();
		final boolean x = in.InsertInDataBaseWithCols(map, tableName, path);
		return x;
	}



	public static final int getValuesFromUpdateQuery(String sql, String condition ,String tableName,String path) throws SQLException {
		final Map<String, String> map = new HashMap<>();
		String[] parts1 = null;
		sql = sql.substring(sql.indexOf("SET") + 3, sql.length());
		final String[] parts = sql.trim().split(",");
		for (int i = 0; i < parts.length; i++) {
			parts1 = parts[i].split("=");
			char n;
			for (int j = 0; j < parts1[1].length(); j++) {
				n = parts1[1].charAt(j);
				if (n == '\'') {
					parts1[1] = parts1[1].substring(j + 1, parts1[1].length() - 1);
				}
			}
			map.put(parts1[0].trim(), parts1[1].trim());
		}
		int z = 0;
		final Context context = new Context(new Update());

		if(condition != null) {
			z =  context.executeStrategy7(map, condition, tableName, path, columnNames);
		}
		else {
		z =  context.executeStrategy6(map, tableName, path, columnNames);
		}
		/*final Update up = new Update();
		int z = 0;
		if(condition != null) {
			z = up.UpdateDataBaseWithCondition(map, condition, tableName, path, columnNames);
		}
		else {
			z = up.UpdateDataBaseWithOutCondition(map, tableName, path, columnNames);
		}*/
		return z;
	}
}
