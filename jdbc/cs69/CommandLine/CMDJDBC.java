package eg.edu.alexu.csd.oop.jdbc.cs69.CommandLine;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.cs69.CommandLine.CMD;
import eg.edu.alexu.csd.oop.jdbc.cs69.Driver.DBDriver;

public class CMDJDBC {
	private static Statement stmt;
	private static Connection connection;
	private static DBDriver driver;
	static Scanner input;
	private static void GetConnection() throws SQLException {
		final String url = "jdbc:xmldb://localhost";
        final Properties info = new Properties();
        final File dbDir = new File("jdbcFile");
        info.put("path", dbDir.getAbsoluteFile());
        driver = new DBDriver();
        connection = driver.connect(url, info);
        stmt = connection.createStatement();
	}
	static void printStart() {
        System.out.println("Enter your Query");
        System.out.print(">> ");
    }
	static String getUserInput() {
        input = new Scanner(System.in);
        final String userInput = input.nextLine();
        return userInput;
    }
	static void executeQuery() throws SQLException{
		final CMD cmd=new CMD();
		String query;
		query = getUserInput();
		if(query.toUpperCase().contains("CREATE")||query.toUpperCase().contains("DROP")||query.contains("SELECT")){
			final boolean c=stmt.execute(query);
			if(query.contains("SELECT")){
		    System.out.println(c);
			cmd.getandexcute(query);
			}else if(query.toUpperCase().contains("CREATE")){
				System.out.println(!c);
			}else{
				System.out.println(c);
			}
		}else if(query.contains("INSERT")||query.toUpperCase().contains("DELETE")||query.toUpperCase().contains("UPDATE")){
			final int n=stmt.executeUpdate(query);
			if(n==0){
				System.out.println("Faild to update table");
			}else{
				System.out.println("number of rows that get changed ( "+n+" )");
			}
		}
	}
	 public static void main(String[] args) throws SQLException {
		 try {
	            GetConnection();
	            while (true) {
	                printStart();
	                executeQuery();
	            }

	        } catch (final Exception e) {
	            throw new SQLException();
	        }
	 }
}
