package eg.edu.alexu.csd.oop.db.cs69.Model.Operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eg.edu.alexu.csd.oop.db.cs69.DTD.DTD;
import eg.edu.alexu.csd.oop.db.cs69.DTD.DTDException;
import eg.edu.alexu.csd.oop.db.cs69.Model.MyDatabase;
import eg.edu.alexu.csd.oop.db.cs69.Model.Strategy.Strategy;

public class Create implements Strategy{
	public static boolean check;
	 static File theDir;

	public static ArrayList<String> changeToArrayList(String[] myarray) {

		final ArrayList<String> requiredList = new ArrayList<String>();
		for (int i = 0; i < myarray.length; i++) {
			requiredList.add(myarray[i]);
		}
		return requiredList;

	}


		@Override
		public void Database(String databaseName) {

			check=false;
			databaseName = databaseName.toUpperCase();
			final String s = MyDatabase.myPath;
			theDir = new File(s + databaseName);
			MyDatabase.dB = databaseName;
			theDir.mkdirs();
			MyDatabase.firstCreated = true;
			check= true;
			return;

		}

		@Override
		public  void createTable(String tableName, String[] columnNames , String[] type) {
			check=false;

			tableName = tableName.toUpperCase();

			final ArrayList<String> columnList = changeToArrayList(columnNames);
			final ArrayList<String> typeList = changeToArrayList(type);

			final File f = new File(MyDatabase.myPath+MyDatabase.dB+System.getProperty("file.separator")+tableName+".xml");
			try {
				final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				final Document document = documentBuilder.newDocument();
				final Element element2 = document.createElement(tableName);
				document.appendChild(element2);
				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				try {
					final Transformer trasformer = transformerFactory.newTransformer();
					final DOMSource source = new DOMSource(document);
					trasformer.setOutputProperty(OutputKeys.INDENT,"yes");
					trasformer.setOutputProperty(OutputKeys.METHOD,"xml");
					trasformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
					final StreamResult streamResult = new StreamResult(new File(MyDatabase.myPath+MyDatabase.dB+System.getProperty("file.separator")+tableName+".xml"));
					trasformer.transform(source,streamResult);
					final DTD dtd=new DTD(MyDatabase.myPath+MyDatabase.dB+System.getProperty("file.separator")+tableName+".dtd");
					DTD.writeDTD( tableName, columnList, typeList);
				} catch (final TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("ca");
				} catch (final TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("cz");
				} catch (final DTDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("ce");
				}

			} catch (final ParserConfigurationException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("c");
			}
			check= true;



			return;

		}


		@Override
		public void dropTable(String tableName) {};


		@Override
		public Object[][] selectColumnsCondition(String[] columns, String tableName, String condition, String path) {
			return null;}


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


