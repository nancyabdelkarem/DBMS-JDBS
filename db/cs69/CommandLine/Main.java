package eg.edu.alexu.csd.oop.db.cs69.CommandLine;

import java.sql.SQLException;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;

public class Main {
	static Scanner input = new Scanner(System.in);
	static CMD cmd;
	static void printStart() {
		System.out.println("Enter your Query");
		System.out.print(">> ");
	}
	static String getUserInput() {
		String query = input.nextLine();
		return query;
	}
	static void excute() throws SQLException{
		String query = getUserInput();
		cmd.getandexcute(query);
	}
	public static void main(String[] args) {
		MyDatabase m=new MyDatabase();
		 m.createDatabase("TestDB", true);
		cmd = new CMD();
		while (true) {
			printStart();
			try {
				excute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
