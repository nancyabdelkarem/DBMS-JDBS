package eg.edu.alexu.csd.oop.db.cs69.Control;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;
import eg.edu.alexu.csd.oop.db.cs69.Model.SQLParser;
import eg.edu.alexu.csd.oop.db.cs69.View.GUI;

 public class Control {
	GUI g= new GUI();
	MyDatabase db=new MyDatabase();
    SQLParser p = new SQLParser();
     String[] Columns=p.getColumns();
    public Control(String query){

    	 if (query.toLowerCase().contains("create") || query.toLowerCase().contains("drop")) {
			 database(query);
			 }
			 else if(query.toLowerCase().contains("insert") || query.toLowerCase().contains("update") || query.toLowerCase().contains("delete")) {
					try {
						operation(query);

					} catch (final Exception e) {
						JOptionPane.showMessageDialog(null, "Please enter a correct query");
					}
				}
			 else if (query.toLowerCase().contains("select")) {
					try {

						final Object [][] array = select(query);
						final JFrame f = new JFrame("Database Table");
					   
					    final Container content = f.getContentPane();
						final JTable table = new JTable(array, Columns);
						final JScrollPane scrollPane = new JScrollPane(table);
					    content.add(scrollPane, BorderLayout.CENTER);
					    f.setSize(500, 500);
					    f.setVisible(true);
					} catch (final Exception e) {
						JOptionPane.showMessageDialog(null, "Please enter a correct query");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter a correct query");
				}

    }

	public Object[][] select(String query){

		Object[][] array=null;
		try {
			array = db.executeQuery(query);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;

}
public void database(String query){
		if (query.toLowerCase().contains("create") && query.toLowerCase().contains("database")) {
			query = query.toUpperCase();
			query = query.replace("CREATE", "");
			query = query.replace("DATABASE", "");
			final String databaseName = query.trim();
			if(g.chckbxNewCheckBox.isSelected()) {

				db.createDatabase(databaseName, true);
			}
			else {
				db.createDatabase(databaseName, false);
			}
			System.out.println(g.chckbxNewCheckBox.isSelected());
		}

		else if (query.toLowerCase().contains("create") || query.toLowerCase().contains("drop")) {
			try {
				db.executeStructureQuery(query);
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}


	}
	void operation(String query){
		try {
			db.executeUpdateQuery(query);
		} catch (final SQLException e) {

			e.printStackTrace();
		}
	}
}
