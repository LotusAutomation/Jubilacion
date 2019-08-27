package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.util.ArrayList;

import org.w3c.dom.*;
import org.json.*;

public class ElementosJson {

	public ElementosJson() {
		super();

	}

	public static final String TYPEDATASIMPLE = "simple";
	public static final String TYPEDATAMULTILINE = "multiLine";
	public static final String TYPEDATASIMPLEDATE = "simpleDate";

	private static String number = "number";
	private static String datetime = "datetime";
	private static String par = "par";
	private static String breakXML = "break";
	private static String text = "text";
	private static String datetimelist = "datetimelist";
	private static String richtext = "richtext";

	FormatosCampos formatosCampos = new FormatosCampos();

	public static Integer iContador = 0;

	// readField ArrayList
	public static void readField(String universalID, String keyField, ArrayList valueFieldJSON, Document doc) {
		String valueField = "";
		ElementosJson readJSONFile = new ElementosJson();
		ElementosXml readXMLFile = new ElementosXml();

		NodeList itemList = readXMLFile.getItems(doc, keyField);

		try {
			for (int i = 0; i < itemList.getLength(); i++) {
				Element firstItemElement = (Element) itemList.item(i);
				String nodeType = firstItemElement.getFirstChild().getNodeName();
				if (nodeType.equalsIgnoreCase(text)) {
					NodeList textFNList = firstItemElement.getElementsByTagName(nodeType);
					Element firstTextElement = (Element) textFNList.item(0);
					valueField = (String.join("", valueFieldJSON)).trim();
					String resultado = (readJSONFile.getValueItem(firstTextElement, valueField, TYPEDATAMULTILINE));

					// ------------Escribir en Archivo de evidencia----------------
					if (resultado != "") {
						ListaDeFallidos.validarFallidos(resultado, universalID, keyField, valueField);
					}
					// ------------Escribir en Archivo de evidencia----------------

				} else {
					// Validate numberlist, textlist, datetimelist
					Node node = (Node) firstItemElement.getFirstChild();
					NodeList nodesValues = (NodeList) node.getChildNodes();
					if (nodeType.equalsIgnoreCase(datetimelist)) {
						for (int j = 0; j < nodesValues.getLength(); j++) {
							valueField = ((String) valueFieldJSON.get(j)).trim();
							Element firstTextElement = (Element) nodesValues.item(j);
							boolean time = readJSONFile.findTime(firstTextElement);
							valueField = readJSONFile.formatosCampos.formatDate(valueField, time, false);
							String resultado = (readJSONFile.getValueItem(firstTextElement, valueField,
									TYPEDATASIMPLE));

							// ------------Escribir en Archivo de evidencia----------------
							if (resultado != "") {
								ListaDeFallidos.validarFallidos(resultado, universalID, keyField, valueField);
							}
							// ------------Escribir en Archivo de evidencia----------------

						}
					} else {
						if (valueFieldJSON.size() > 0) {
							if (nodesValues.getLength() != valueFieldJSON.size()) {
								valueFieldJSON = readJSONFile.formatosCampos.originalArray(valueFieldJSON);
							}
						}
						for (int j = 0; j < valueFieldJSON.size(); j++) {
							String resultado = "";
							Element firstTextElement = (Element) nodesValues.item(j);
							valueField = ((String) valueFieldJSON.get(j)).trim();
							if (keyField.substring(0, 2).equalsIgnoreCase("dt")) {
								resultado = (readJSONFile.getValueItem(firstTextElement, valueField,
										TYPEDATASIMPLEDATE));
							} else {
								resultado = (readJSONFile.getValueItem(firstTextElement, valueField, TYPEDATASIMPLE));
							}

							// ------------Escribir en Archivo de evidencia----------------
							if (resultado != "") {
								ListaDeFallidos.validarFallidos(resultado, universalID, keyField, valueField);
							}
							// ------------Escribir en Archivo de evidencia----------------

						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR:: readField ArrayList " + e);
		}

	}

	// readField String
	public static void readField(String universalID, String keyField, String valueFieldJSON, Document doc) {
		ElementosJson readJSONFile = new ElementosJson();
		ElementosXml readXMLFile = new ElementosXml();
		String valueField = "";
		NodeList itemList = readXMLFile.getItems(doc, keyField);

		try {
			for (int i = 0; i < itemList.getLength(); i++) {
				Element firstItemElement = (Element) itemList.item(i);
				String nodeType = firstItemElement.getFirstChild().getNodeName();
				NodeList textFNList = firstItemElement.getElementsByTagName(nodeType);
				
				if (keyField.substring(0, 3).equalsIgnoreCase("dat")||keyField.substring(0, 15).equalsIgnoreCase("OriginalModTime")) {
					Element firstTextElement = (Element) textFNList.item(0);
					valueField = readJSONFile.formatosCampos
							.formatText(firstTextElement.getFirstChild().getNodeValue().trim());
					valueField = readJSONFile.formatosCampos.formatDateString(valueField);
					//System.out.println("ElementosJson valueField::." + valueField);
					// ------------Escribir en Archivo de evidencia----------------
					if (valueField != "" && valueField != null) {
						ListaDeFallidos.validarFallidos(valueField, universalID, keyField, valueFieldJSON);
					}
					// ------------Escribir en Archivo de evidencia----------------
				}
				else if (nodeType.equalsIgnoreCase(richtext)) {
						Element firstTextElement = (Element) textFNList.item(0);
						String resultado = (readJSONFile.getValueItem(firstTextElement, valueFieldJSON, TYPEDATAMULTILINE));
						// ------------Escribir en Archivo de evidencia----------------
						if (resultado != "" && resultado != null) {
							ListaDeFallidos.validarFallidos(resultado, universalID, keyField, valueFieldJSON);
						}
						// ------------Escribir en Archivo de evidencia----------------
	
					} else {
						// Validate number, text, datetime
						if (nodeType.equalsIgnoreCase(datetime)) {
							Element firstTextElement = (Element) textFNList.item(0);
							boolean time = readJSONFile.findTime(firstTextElement);
							valueFieldJSON = readJSONFile.formatosCampos.formatDate(valueFieldJSON, time, false);
	
							// ------------Escribir en Archivo de evidencia----------------
							if (valueFieldJSON != "") {
								if (valueFieldJSON.substring(0, 7).equalsIgnoreCase("fallido")) {
									String[] valorCampoXml = valueFieldJSON.split("\\$-");
									ListaDeFallidos.retornarListaFallidos(universalID, keyField, valueFieldJSON,
											valorCampoXml[1]);
	
								}
							}
							// ------------Escribir en Archivo de evidencia----------------
						}
						travelValue(universalID, keyField, textFNList, valueFieldJSON);
					}
			}
		} catch (Exception e) {
			System.out.println("ERROR:: readField String " + e);
		}
	}

	private boolean findTime(Element element) {
		String value = "";
		try {
			value = element.getFirstChild().getNodeValue().trim();
		} catch (Exception e) {
			System.out.println("ERROR:: findTime 'T' " + e);
		}

		return value.contains("T");
	}

	private static void travelValue(String universalID, String keyField, NodeList textFNList, String valueFieldJSON) {
		ElementosJson readJSONFile = new ElementosJson();

		try {

			for (int j = 0; j < textFNList.getLength(); j++) {
				Element firstTextElement = (Element) textFNList.item(j);
				String resultado = "";

				if (keyField.substring(0, 3).equalsIgnoreCase("dtm")) {
					resultado = readJSONFile.getValueItem(firstTextElement, valueFieldJSON, TYPEDATASIMPLEDATE);
				} else if (keyField.substring(0, 2).equalsIgnoreCase("dt")) {
					resultado = readJSONFile.getValueItem(firstTextElement, valueFieldJSON, TYPEDATASIMPLEDATE);
				} else {
					resultado = readJSONFile.getValueItem(firstTextElement, valueFieldJSON, TYPEDATASIMPLE);
				}

				// ------------Escribir en Archivo de evidencia----------------
				if (resultado != "" && resultado != null) {
					ListaDeFallidos.validarFallidos(resultado, universalID, keyField, valueFieldJSON);
				}
				// ------------Escribir en Archivo de evidencia----------------

			}
		} catch (Exception e) {
			System.out.println("ERROR:: travelValue  " + e);
		}
	}

	private String getValueItem(Element textElement, String valueFieldJSON, String type) {
		ElementosJson readJSONFile = new ElementosJson();
		String valueField = "";
		String texto = "";
		// Arreglo de tag que vienen en el Xml que no traen información útil
		String[] tagsIgnore = { "javascript", "attachmentref", "picture", "notesbitmap", "formula", "embeddedkeywords",
				"button", "popuptext", "lotusscript", "filedata" };
		try {

			if (textElement.getFirstChild() != null) {
				if (textElement.getFirstChild().getNodeName() != "break") {
					NodeList nodesBreak = (NodeList) textElement.getElementsByTagName(breakXML);
					if (nodesBreak.getLength() >= 0) {
						valueField = readJSONFile.formatosCampos.formatText(textElement.getTextContent());
					} else {
						valueField = readJSONFile.formatosCampos
								.formatText(textElement.getFirstChild().getNodeValue().trim());
					}
					if (number.equals(textElement.getFirstChild().getNodeName())
							|| number.equals(textElement.getNodeName())) {
						valueField = readJSONFile.formatosCampos.formatNumber(valueField);
						valueFieldJSON = readJSONFile.formatosCampos.formatNumber(valueFieldJSON);
					} else if (datetime.equals(textElement.getNodeName())) {
						boolean time = readJSONFile.findTime(textElement);
						valueField = readJSONFile.formatosCampos.formatDate(valueField, time, true);
					}
					if (type.equals(TYPEDATAMULTILINE)) {
						valueField = readJSONFile.formatosCampos.formatText(textElement.getTextContent().trim());
						NodeList nodesPar = (NodeList) textElement.getElementsByTagName(par);

						if (nodesPar.getLength() <= 0 && nodesBreak.getLength() <= 0) {
							String[] valueFieldTemp = valueField.split(";");
							valueField = (String.join("", valueFieldTemp)).trim();
						} else if (nodesPar.getLength() > 0) {
							String allDataPar = "";
							for (int i = 0; i < nodesPar.getLength(); i++) {
								Node firstParNode = nodesPar.item(i);
								Element firstParElement = (Element) nodesPar.item(i);

								try {
									// Método que elimina los tags que no traen información relevante
									ElementosXml.removeAllNode(firstParNode, firstParNode.getNodeType(), tagsIgnore);
									ElementosXml.removeAllNode(firstParNode, Node.ELEMENT_NODE, tagsIgnore);

								} catch (Exception e) {
									System.out.println("ElementosXml.removeAll ERROR::: " + e);
								}

								allDataPar += readJSONFile.formatosCampos.formatText(firstParNode.getTextContent());

							}

							valueField = allDataPar.trim();
						}
					} else if (type.equals(TYPEDATASIMPLEDATE) && textElement.getNodeName().equals("text")) {
						if (textElement.getFirstChild().getNodeValue() != null) {
							valueField = readJSONFile.formatosCampos
									.formatText(textElement.getFirstChild().getNodeValue().trim());
							if (!valueField.equals("") && !valueField.equals("-"))
								valueField = readJSONFile.formatosCampos.formatDateString(valueField);
						}
					}
					valueFieldJSON = valueFieldJSON.trim();
					valueField = valueField.trim();
					if (valueFieldJSON.equalsIgnoreCase(valueField)) {
						texto = "La información de éstos campos SI ES IGUAL ";
					} else {
						texto = "fallido$-" + valueField + "|";
					}
				}
			}

		} catch (Exception e) {
			System.out.println(".::ERROR::. " + e);
		}
		return texto;
	}

}