package eg.edu.alexu.csd.oop.db.cs69.Model.Operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.db.cs69.Model.Strategy.Strategy;

public   class Select implements Strategy{
	Object[][] array;

	@Override
	public Object[][] selectColumns(String[] columns, String tableName, String path) {
		final File xmlFile = new File(path + "/" + tableName + ".xml");
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(xmlFile);
			final NodeList list = doc.getElementsByTagName("row");
			final ArrayList<ArrayList<Object>> array1 = new ArrayList();
			ArrayList<Object> array2 = new ArrayList();
			for (int temp = 0; temp < list.getLength(); temp++) {
				array2 = new ArrayList();
				for (int i = 0; i < columns.length; i++) {
					final Node nNode = list.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						final Element eElement = (Element) nNode;
						if (eElement.getElementsByTagName(columns[i]).item(0) != null) {
							array2.add(eElement.getElementsByTagName(columns[i]).item(0).getTextContent());
						}
					}
				}
				array1.add(array2);
			}
			int i = 0;
			int j = 0;
			final Object[][] array3 = new Object[array1.size()][array2.size()];
			for (final ArrayList obj : array1) {
				if (obj.size() != 0) {
					for (j = 0; j < obj.size(); j++) {
						array3[i][j] = obj.get(j);
					}
					i++;
				}
			}
			array = new Object[i][j];
			for (int k = 0; k < i; k++) {
				for (int l = 0; l < j; l++) {
					array[k][l] = array3[k][l];
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public Object[][] selectColumnsCondition(String[] columns, String tableName, String condition, String path) {
		final File xmlFile = new File(path + "/" + tableName + ".xml");
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(xmlFile);
			final NodeList list = doc.getElementsByTagName("row");
			final Object[][] array2 = new Object[list.getLength()][columns.length];
			int k = 0;
			int r = 0;
			String[] split = null;
			String columnName = null;
			String value = null;
			if (condition.contains("=")) {
				split = condition.split("=");
				columnName = split[0].trim();
				value = split[1].replace("'", "").trim();
				for (int temp = 0; temp < list.getLength(); temp++) {
					r = 0;
					final Node nNode = list.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						final Element eElement = (Element) nNode;
						if (eElement.getElementsByTagName(columnName).item(0) != null) {
							final String s = eElement.getElementsByTagName(columnName).item(0).getTextContent();
							if (s.equals(value)) {
								for (int i = 0; i < columns.length; i++) {
									if (eElement.getElementsByTagName(columns[i]).item(0) != null) {
										array2[k][r] = eElement.getElementsByTagName(columns[i]).item(0)
												.getTextContent();
										r++;
									}
								}
								k++;
							}
						}
					}
				}
			} else if (condition.contains("<")) {
				split = condition.split("<");
				columnName = split[0].trim();
				value = split[1].trim();
				for (int temp = 0; temp < list.getLength(); temp++) {
					r = 0;
					final Node nNode = list.item(temp);
					System.out.println(nNode);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						final Element eElement = (Element) nNode;
						if (eElement.getElementsByTagName(columnName).item(0) != null) {
							final String s = eElement.getElementsByTagName(columnName).item(0).getTextContent();
							final int x = Integer.parseInt(s);
							if (x < Integer.parseInt(value)) {
								for (int i = 0; i < columns.length; i++) {
									if (eElement.getElementsByTagName(columns[i]).item(0) != null) {
										array2[k][r] = eElement.getElementsByTagName(columns[i]).item(0)
												.getTextContent();
										r++;
									}
								}
								k++;
							}
						}
					}
				}
			} else if (condition.contains(">")) {
				split = condition.split(">");
				columnName = split[0].trim();
				value = split[1].trim();
				for (int temp = 0; temp < list.getLength(); temp++) {
					r = 0;
					final Node nNode = list.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						final Element eElement = (Element) nNode;
						if (eElement.getElementsByTagName(columnName).item(0) != null) {
							String s = eElement.getElementsByTagName(columnName).item(0).getTextContent();
							s = s.replace("'", "");
							final int x = Integer.parseInt(s);
							if (x > Integer.parseInt(value)) {
								for (int i = 0; i < columns.length; i++) {
									if (eElement.getElementsByTagName(columns[i]).item(0) != null) {
										array2[k][r] = eElement.getElementsByTagName(columns[i]).item(0)
												.getTextContent();
										r++;
									}
								}
								k++;
							}
						}
					}
				}
			} else {
				throw null;
			}
			array = new Object[k][columns.length];
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < array2[0].length; j++) {
					if(i==0 && j==1){
						array[i][j]=Integer.parseInt(array2[i][j].toString());
						j++;
					}
					array[i][j] = array2[i][j];
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	@Override
	public void Database(String databaseName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropTable(String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTable(String tableName, String[] columnNames, String[] type) {
		// TODO Auto-generated method stub

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