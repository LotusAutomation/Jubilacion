package co.com.bancolombia.certification.compararjsonvsxml.utils;

import static co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararJson.rutaFileXmlHijos;
import static co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararJson.rutaFileXmls;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ElementosXml {

	public static Document getDocumentByUniversalId(String universalID, boolean hijos) {

		// Elige la ruta correcta para cada caso, la de xmlPadre o xmlHijos
		String fileNameXML = ((!hijos) ? ReplaceCharacters.of(rutaFileXmls) : ReplaceCharacters.of(rutaFileXmlHijos))
				+ universalID + ".xml";

		System.out.println("********************************** " + fileNameXML);

		try {
			// Read XML by document
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			if (docBuilder.parse(fileNameXML) != null) {

				return docBuilder.parse(fileNameXML);
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {

			e.printStackTrace();
			System.out.println(".::Elementos XML: EL sgte XML está vacío o no existe: " + universalID);
		}
		return null;
	}

	public NodeList getItems(Document doc, String keyField) {

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);

			XPathFactory factory = XPathFactory.newInstance();

			XPath xpath = factory.newXPath();
			String expression = "//*[@name='" + keyField + "']";
			NodeList itemList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			if (keyField.equalsIgnoreCase("ok1_1")) {

				System.out.println("itemList ** " + itemList.getLength());
			}
			return itemList;

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Eliminar los tags hijos del enviado por parámetro
	public static void removeAll(Node node, short nodeType, String name) {

		if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {

			node.getParentNode().removeChild(node);

		} else {

			NodeList list = node.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {

				removeAll(list.item(i), nodeType, name);
			}
		}
	}

	public static void removeAllNode(Node node, short nodeType, String[] name) {

		for (int i = 0; i < name.length - 1; i++) {

			if (node.getNodeType() == nodeType && (name[i] == null || node.getNodeName().equals(name[i]))) {

				node.getParentNode().removeChild(node);

			} else {

				NodeList list = node.getChildNodes();

				for (int j = 0; j < list.getLength(); j++) {

					removeAllNode(list.item(j), nodeType, name);
				}
			}
		}

	}

}
