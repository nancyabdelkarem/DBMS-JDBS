package eg.edu.alexu.csd.oop.db.cs69.Model;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter.AndExpression;
import eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter.Expression;
import eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter.TerminalExpression;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Create;
import eg.edu.alexu.csd.oop.db.cs69.Model.Operation.Drop;

class QueryFactory {
	Expression createData() {
		final Expression q1 = new TerminalExpression("CREATE");
		final Expression q2 = new TerminalExpression("DATABASE");
		return new AndExpression(q1, q2);
	}

	Expression dropTable() {
		final Expression q1 = new TerminalExpression("DROP");
		final Expression q2 = new TerminalExpression("TABLE");
		return new AndExpression(q1, q2);
	}

	Expression createTable() {
		final Expression q1 = new TerminalExpression("CREATE");
		final Expression q2 = new TerminalExpression("TABLE");
		return new AndExpression(q1, q2);
	}

	Expression dropData() {
		final Expression q1 = new TerminalExpression("DROP");
		final Expression q2 = new TerminalExpression("DATABASE");
		return new AndExpression(q1, q2);
	}

	final Expression Q = createData();
	final Expression Q1 = dropTable();
	final Expression Q2 = dropData();
	final Expression Q3 = createTable();
	final SQLParser parser = new SQLParser();
	
	boolean createWithout = false;
	public Object getQuery(String query) throws SQLException {

		query = query.trim();

		if (Q.interpret1(query)) {
			createWithout = true;
			parser.createDatabaseParser(query);
		

			return new Create();
		} else if (Q2.interpret1(query)) {
			createWithout = false;
			parser.dropDatabaseParser(query);
			return new Drop();
		} else if (Q3.interpret1(query)) {

			if (!query.contains("(")) {
				throw new SQLException();
			}
			//if (createWithout) {
				parser.createTableParser(query);
				return new Create();
//		} else
//			{
//				throw new SQLException();
//		}

			
		} else if (Q1.interpret1(query)) {
//			if (createWithout) {
				parser.dropTableParser(query);
				return new Drop();
			}
//		} else {
//			throw new SQLException();
//		}

		throw new SQLException();
	}

}
