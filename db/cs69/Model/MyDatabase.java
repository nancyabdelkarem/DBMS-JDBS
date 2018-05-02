package eg.edu.alexu.csd.oop.db.cs69.Model;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter.AndExpression;
import eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter.Expression;
import eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter.TerminalExpression;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Create;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Delete;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Drop;
import eg.edu.alexu.csd.oop.db.cs69.Model.Strategy.Context;

public class MyDatabase implements Database {
	public static File theDir;
	public static String myPath = "";
	static String path = "";
	public static boolean firstCreated;
	public static String dB = "";

	private String tableName;
	public String gettablename(){
		return tableName;
	}

	final Expression q_ins = new TerminalExpression("INSERT");
	final Expression q_cr = new TerminalExpression("CREATE");
	final Expression q_dr = new TerminalExpression("DROP");
	final Expression q_del = new TerminalExpression("DELETE");
	final Expression q_up = new TerminalExpression("UPDATE");
	final Expression q_wh = new TerminalExpression("WHERE");
	final Expression q_fr = new TerminalExpression("FROM");
	final Expression q = new TerminalExpression("*");

	Expression select() {
		final Expression q1 = new TerminalExpression("SELECT");
		final Expression q2 = new TerminalExpression("FROM");
		return new AndExpression(q1, q2);

	}

	final Expression Q4 = select();

	public MyDatabase() {
		final File f = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Database");
		f.mkdirs();
		myPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "Database"
				+ System.getProperty("file.separator");
		firstCreated = false;
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		// TODO Auto-generated method stub
		databaseName = databaseName.toUpperCase();
		final File theDir = new File(myPath + databaseName);
		// if the directory does not exist, create it
		firstCreated = true;
		dB = databaseName;
		if (dropIfExists == true && theDir.exists()) {
			try {
				executeStructureQuery("DROP DATABASE " + databaseName);
				executeStructureQuery("CREATE DATABASE " + databaseName);
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			if (!theDir.exists()) {
				try {

					executeStructureQuery("CREATE DATABASE " + databaseName);
				} catch (final SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				return theDir.getAbsolutePath();

			}
		}
		return theDir.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		query = query.toUpperCase();
		final QueryFactory qf = new QueryFactory();
		if (q_cr.interpret1(query)) {
			final Create c = (Create) qf.getQuery(query);

			return c.check;
		} else if (q_dr.interpret1(query)) {
			final Drop d = (Drop) qf.getQuery(query);

			return d.check;
		}

		else {
			throw new SQLException();
		}

	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {

		final SQLParser p = new SQLParser();
		Object[][] array = null;
		query = query.toUpperCase().trim();
		if (Q4.interpret1(query)) {
			final SQLParser parser = new SQLParser();
			if (q.interpret(query) && !q_wh.interpret(query)) {
				array = parser.selectAllParser(query, myPath + dB);
			} else if (q.interpret(query) && q_wh.interpret(query)) {
				array = parser.selectAllWhereParser(query, myPath + dB);
			} else if (!q_wh.interpret(query)) {
				array = parser.selectParser(query, myPath + dB);
			} else if (q_wh.interpret(query)) {
				array = parser.selectWhereParser(query, myPath + dB);
			}
		} else {
			throw new SQLException();
		}

		return array;

	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		int z = 0;
		if (query == null || query.trim().length() == 0) {
			throw new SQLException("The query string cannot be empty");
		}
		final SQLParser p = new SQLParser();
		String withoutSpace;
		query = query.trim();
		// query=query.replaceAll("*", "");
		query = query.replaceAll(";", "");
		String ucquery = query.toUpperCase();
		withoutSpace = ucquery.replaceAll("\\s+", "");
		if (q_ins.interpret1(ucquery)) {

			if (withoutSpace.charAt(withoutSpace.indexOf("VALUES") - 1) == ')') {
				tableName = ucquery.substring(ucquery.indexOf("INTO") + 4, ucquery.indexOf("("));
				tableName = tableName.trim();

				final boolean x = p.InsertQueryWithCols(ucquery, tableName, myPath + dB);
				if (x) {
					z++;
				}
			} else {
				tableName = ucquery.substring(ucquery.indexOf("INTO") + 4, ucquery.indexOf("VALUES"));
				tableName = tableName.trim();
				final boolean x = p.InsertQueryWithoutCols(ucquery, tableName, myPath + dB);
				if (x) {
					z++;
				}
			}
		} else if (q_up.interpret1(ucquery)) {
			tableName = ucquery.substring(ucquery.indexOf("UPDATE") + 6, ucquery.indexOf("SET"));
			tableName = tableName.trim();
			if (q_wh.interpret(ucquery)) {
				final String condition = ucquery.substring(ucquery.indexOf("WHERE") + 5);
				ucquery = ucquery.substring(0, ucquery.indexOf("WHERE"));
				z = p.getValuesFromUpdateQuery(ucquery.trim(), condition.trim(), tableName, myPath + dB);
			} else if (!q_wh.interpret(ucquery)) {
				z = p.getValuesFromUpdateQuery(ucquery, null, tableName, myPath + dB);
			}
		} else if (q_del.interpret1(ucquery)) {
			final Context context = new Context(new Delete());

			if (q_wh.interpret(ucquery)) {
				tableName = ucquery.substring(ucquery.indexOf("FROM") + 4, ucquery.indexOf("WHERE"));
				tableName = tableName.trim();
				String condition = ucquery.substring(ucquery.indexOf("WHERE") + 5);
				condition = condition.trim();

				z = context.executeStrategy8(condition, tableName, myPath + dB);
			} else {
				tableName = ucquery.substring(ucquery.indexOf("FROM") + 4);
				tableName = tableName.trim();

				z = context.executeStrategy9(tableName, myPath + dB);
			}

		} else {
			throw new SQLException("The query string is neither an insert nor an update nor a delete");
		}

		return z;
	}

	public String[] getColumns() {
		final SQLParser p = new SQLParser();
		return p.getColumns();
	}

	public String[] getType() {
		final SQLParser p = new SQLParser();
		return p.getType();
	}

	public String getTableName() {
		final SQLParser p = new SQLParser();
		return p.getTableName();
	}

}
