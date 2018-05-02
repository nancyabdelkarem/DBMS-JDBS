package eg.edu.alexu.csd.oop.db.cs69.DTD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DTD {
	private final String path;
	private static File dtdFile;
	private static BufferedWriter buffer;
	private static FileWriter writer;
	private final static String newLine = System.getProperty("line.separator");

	public DTD(String path) {
		this.path = path;
		dtdFile = new File(path + ".dtd");
	}

	public static void writeDTD(String tableName, ArrayList<String> columns, ArrayList<String> types)
			throws DTDException {

		createFile(tableName);
		writeInFile(tableName, columns, types);

	}

	public static ArrayList<ArrayList<String>> readFromFile(String tableName) {
		ArrayList<ArrayList<String>> required = new ArrayList<ArrayList<String>>();
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<String> types = new ArrayList<String>();
		String cName;
		String cType;
		try {
			final Scanner scanner = new Scanner(new File("TESTDB/" + tableName + ".dtd"));
			String line = "";
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				// System.out.println(line);
				if ((line.contains("<!DOCTYPE") || (line.contains("<!ELEMENT")))) {
					// if (!tableName.equals(scanner.next())) {
					//// System.out.println("hh");
					// }
				} else if (line.contains("<!ATTLIST")) {
					String[] split = line.split(" ");
					cName = split[1];
					cType = split[4];
//					System.out.println(cName);
					cType = cType.replace(">", "");
					cType = cType.replace("\"", "");
//					System.out.println(cType);
					types.add(cType);
					columnNames.add(cName);

				}
				required.add(columnNames);
				required.add(types);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return required;

	}

	private static void createFile(String fileName) throws DTDException {

		dtdFile.getParentFile().mkdirs();
		try {
			dtdFile.createNewFile();
		} catch (final IOException e) {
			throw new DTDException("ERROR in creating the file");

		}

	}

	private static void writeInFile(String tableName, ArrayList<String> columns, ArrayList<String> types)
			throws DTDException {
		try {
			writer = new FileWriter(dtdFile.getAbsoluteFile());
			buffer = new BufferedWriter(writer);

			writeStart(tableName);
			writeElements(columns, types);

			buffer.close();
			writer.close();

		} catch (final IOException e) {
			throw new DTDException("ERROR in writing the file");
		}

	}

	private static void writeStart(String tableName) throws DTDException {
		final String table = "<!ELEMENT " + tableName + " (TableIdentifier, Row*)>" + newLine;
		try {
			buffer.write(table);
		} catch (final IOException e) {
			throw new DTDException("ERROR in writing the file begining");

		}

	}

	private static void writeElements(ArrayList<String> columns, ArrayList<String> types) throws DTDException {
		String elements = "";
		String identifier = "<!ELEMENT TableIdentifier (";
		String row = "<!ELEMENT Row (";

		for (int i = 0; i < columns.size(); i++) {
			if (i > 0) {
				identifier += ", ";
				row += ", ";
			}
			identifier += columns.get(i);
			row += columns.get(i);
			elements += "<!ELEMENT " + columns.get(i) + " (#PCDATA)>" + newLine;
			elements += "<!ATTLIST " + columns.get(i) + " type CDATA " + "\"" + types.get(i) + "\">" + newLine;
		}

		identifier += ")>" + newLine;
		row += ")>" + newLine;
		try {
			buffer.write(identifier);
			buffer.write(row);
			buffer.write(elements);
		} catch (final IOException e) {
			throw new DTDException("ERROR in writing the elements");

		}
	}

}
