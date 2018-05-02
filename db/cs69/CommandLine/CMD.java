package eg.edu.alexu.csd.oop.db.cs69.CommandLine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;
import eg.edu.alexu.csd.oop.db.cs69.Model.SQLParser;

public class CMD {
	public void drawTable(String []columnNames,Object[][]selectedvalues) {	
		ArrayList<Integer> columnsWidth;
		List<Object> row= new ArrayList<Object>();
		int tableWidth;
		columnsWidth = ColumnsWidth(columnNames,selectedvalues);
		tableWidth = TableWidth(columnsWidth);
		drawHorizontal(tableWidth, '=');
		List<Object> wordsList = Arrays.asList(columnNames); 
		drawRow(wordsList, columnsWidth, '|');
		drawHorizontal(tableWidth, '=');
		for (int i = 0; i < selectedvalues.length; i++) {
			for(int j = 0; j < selectedvalues[0].length; j++){
				row.add(selectedvalues[i][j]);
			}
			drawRow(row, columnsWidth, '|');
			drawHorizontal(tableWidth, '-');
			row.clear();
		}
		System.out.println("\n");
	}

	private ArrayList<Integer> ColumnsWidth(String []columnNames,Object[][]selectedvalues) {
		ArrayList<Integer> columnsWidth = new ArrayList<Integer>();
		ArrayList<Object> columnsValues= new ArrayList<Object>();
		String colName;
		for (int i = 0; i < columnNames.length; i++) {
			colName = columnNames[i];
			for(int j=0;j<selectedvalues.length;j++){
				columnsValues.add(selectedvalues[j][i]);
			}
			columnsWidth.add(maxColumnWidth(columnsValues, colName.length()));
			columnsValues.clear();
		}
		return columnsWidth;

	}

	private int maxColumnWidth(ArrayList<Object> column, int columnNameSize) {
		int maxWidth = columnNameSize;
		for (int i = 0; i < column.size(); i++) {
			if (((String) column.get(i)).length() > maxWidth) {
				maxWidth = ((String) column.get(i)).length();
			}
		}
		return maxWidth;
	}

	private int TableWidth(ArrayList<Integer> columnsWidth) {
		int totalWidth = 0;
		for (int i = 0; i < columnsWidth.size(); i++) {
			totalWidth += columnsWidth.get(i);
			totalWidth += 3;
		}

		return totalWidth;

	}

	private void drawHorizontal(int tableWidth, char symbol) {
		System.out.print("");
		for (int i = -1; i < tableWidth; i++) {
			System.out.print(symbol);
		}
	}

	private void drawRow(List<Object> rowValues, ArrayList<Integer> columnsWidth, char separator) {
		Object adjustedWord;
		System.out.print("\n" + separator);
		for (int i = 0; i < rowValues.size(); i++) {
			adjustedWord = adjustWordSize(rowValues.get(i), columnsWidth.get(i));
			System.out.print(" " + adjustedWord + " " + separator);
		}
		System.out.print("\n");
	}

	private Object adjustWordSize(Object word, int width) {
		while (((String) word).length() < width) {
			word += " ";
		}
		return word;

	}
	public void getandexcute(String query) throws SQLException{
		MyDatabase m=new MyDatabase();
		SQLParser p=new SQLParser();
		if(query.toUpperCase().contains("CREATE")||query.toUpperCase().contains("DROP")){
			m.executeStructureQuery(query);	
		}else if(query.contains("SELECT")){
			Object[][]selectedvalues=m.executeQuery(query);
			drawTable(p.getSelectedCols(),selectedvalues);
		}else if(query.contains("INSERT")||query.toUpperCase().contains("DELETE")||query.toUpperCase().contains("UPDATE")){
			m.executeUpdateQuery(query);
			Object[][]selectedvalues=m.executeQuery("SELECT * FROM "+m.gettablename());
			drawTable(p.getColsNames(),selectedvalues);
		}else{
			throw new SQLException("invalid Query");
		}
		
	}
}
