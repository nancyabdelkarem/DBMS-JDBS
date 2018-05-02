package eg.edu.alexu.csd.oop.db.cs69.Model.Operation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Insert {

	public boolean InsertInDataBaseWithCols(Map<String, String> invalues, String tableName, String path) {

		final File xmlFile = new File(path + "/" + tableName + ".xml");
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(xmlFile);
			final Node table = doc.getElementsByTagName(tableName).item(0);
			final Element row = doc.createElement("row");
			final Set keyset = invalues.keySet();
			final List<String> keyList = new ArrayList<String>(keyset);
			for (int i = 0; i < keyList.size(); i++) {
				final Element column = doc.createElement(keyList.get(i));
				column.appendChild(doc.createTextNode(invalues.get(keyList.get(i))));
				row.appendChild(column);
			}

			table.appendChild(row);
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer trasformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(doc);
			trasformer.setOutputProperty(OutputKeys.INDENT, "yes");
			trasformer.setOutputProperty(OutputKeys.METHOD, "xml");
			trasformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			final StreamResult streamResult = new StreamResult(path + "/" + tableName + ".xml");
			trasformer.transform(source, streamResult);

		} catch (final TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (final TransformerException e) {
			// TODO Auto-generated catch block
			return false;

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;

	}



}
