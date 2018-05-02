package eg.edu.alexu.csd.oop.db.cs69.Model.Operation;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.db.cs69.Model.Strategy.Strategy;

public  class Update implements Strategy {

	@Override
	public int UpdateDataBaseWithOutCondition(Map<String, String> upvalues, String tableName, String path,
			String[] columnNames) throws SQLException {
		boolean chec = false;
		final Set keyset = upvalues.keySet();
		final List<String> keyList = new ArrayList<String>(keyset);
		int z = 0;
		boolean check = false;
		final File xmlFile = new File(path + "/" + tableName + ".xml");
		if(!xmlFile.exists()) {
			throw new SQLException();
		}
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(xmlFile);
			final NodeList list = doc.getElementsByTagName("row");
			for (int i = 0; i < list.getLength(); i++) {
				final Node node = list.item(i);
				Node n = node.getFirstChild();
				for (int j = 0; j < keyList.size(); j++) {
					while (n.getNextSibling() != null) {
						if (keyList.get(j).equals(n.getNodeName())) {
							n.setTextContent(upvalues.get(keyList.get(j)));
							check = true;
							// System.out.println(z);
						}
						n = n.getNextSibling();
						if (n == null) {
							break;
						}
					}

					n = node.getFirstChild();
				}
				if (check) {
					z++;
				}
				check = false;
			}
			// if (z == 0) {
			// throw new SQLException();
			// }
			// if(chec){
			// throw new SQLException();
			// }
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer trasformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(doc);
			final StreamResult streamResult = new StreamResult(path + "/" + tableName + ".xml");
			trasformer.transform(source, streamResult);
		} catch (final TransformerConfigurationException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (final TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			chec = true;
		}

		return z;
	}

	@Override
	public int UpdateDataBaseWithCondition(Map<String, String> upvalues, String condition, String tableName,
			String path, String[] columnNames) throws SQLException {

		final Set keyset = upvalues.keySet();
		final List<String> keyList = new ArrayList<String>(keyset);
		int z = 0;
		boolean check = false;
		String[] split = null;
		String columnName = null;
		String value = null;
		if (condition.contains("=")) {
			split = condition.split("=");
			columnName = split[0].trim();
			value = split[1].replace("'", "").trim();
			final File xmlFile = new File(path + "/" + tableName + ".xml");
			if(!xmlFile.exists()) {
				throw new SQLException();
			}
			final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			try {
				final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				final Document doc = docBuilder.parse(xmlFile);
				final NodeList list = doc.getElementsByTagName("row");
				for (int temp = 0; temp < list.getLength(); temp++) {
					final Node nNode = list.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						final Element eElement = (Element) nNode;
						if(eElement.getElementsByTagName(columnName).item(0) != null) {
							final String s = eElement.getElementsByTagName(columnName).item(0).getTextContent().trim();
							Node n = nNode.getFirstChild();
							if (s.equals(value)) {
								for (int j = 0; j < keyList.size(); j++) {
									while (n.getNextSibling() != null) {
										if (keyList.get(j).equals(n.getNodeName())) {
											n.setTextContent(upvalues.get(keyList.get(j)));
											check = true;
											// System.out.println(z);
										}
										n = n.getNextSibling();
										if (n == null) {
											break;
										}
									}
									n = nNode.getFirstChild();
								}

							}
							if (check) {
								z++;
							}
							check = false;
						}
					}
				}
				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer trasformer = transformerFactory.newTransformer();
				final DOMSource source = new DOMSource(doc);
				final StreamResult streamResult = new StreamResult(path + "/" + tableName + ".xml");
				trasformer.transform(source, streamResult);
			} catch (final TransformerConfigurationException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			} catch (final TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return z;
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
