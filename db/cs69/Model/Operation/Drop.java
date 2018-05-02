package eg.edu.alexu.csd.oop.db.cs69.Model.Operation;

import java.io.File;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;
import eg.edu.alexu.csd.oop.db.cs69.Model.Strategy.Strategy;

public  class Drop implements Strategy {

	static File theDir;
	public static boolean check;
	@Override
	public  void Database(String databaseName) {
		check=false;
		databaseName = databaseName.toUpperCase();
		theDir = new File(MyDatabase.myPath + databaseName);
		MyDatabase.dB = "";
		try {
			final String[] entries = theDir.list();
			for (int i = 0; i < entries.length; i++) {
				final String s = entries[i];
				final File currentFile = new File(theDir.getPath(), s);
				currentFile.delete();
			}
			theDir.delete();
		} catch (final Exception e) {
			check= false;
			return;
		}


		check= !(theDir.exists());

		return;
	}

	@Override
	public   void dropTable(String tableName) {
		check=false;
		final File table = new File(
				MyDatabase.myPath + MyDatabase.dB + System.getProperty("file.separator") + tableName + ".xml");
		final File table2 = new File(
				MyDatabase.myPath + MyDatabase.dB + System.getProperty("file.separator") + tableName + ".dtd");
		if (table.exists()) {
			try {
				table.delete();
				table2.delete();
			} catch (final Exception e) {
				check= false;
				return;
			}
		} else {
			check= false;
			return;
		}
		check=true;

		return;
	}

	@Override
	public void createTable(String tableName, String[] columnNames, String[] type) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[][] selectColumnsCondition(String[] columns, String tableName, String condition, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[][] selectColumns(String[] columns, String tableName, String path) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int UpdateDataBaseWithOutCondition(Map<String, String> upvalues, String tableName, String path,
			String[] columnNames) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int UpdateDataBaseWithCondition(Map<String, String> upvalues, String condition, String tableName,
			String path, String[] columnNames) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int DeleteDataBaseWhere(String condition, String tableName, String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int DeleteAllDataBase(String tableName, String path) {
		// TODO Auto-generated method stub
		return 0;
	}


}
