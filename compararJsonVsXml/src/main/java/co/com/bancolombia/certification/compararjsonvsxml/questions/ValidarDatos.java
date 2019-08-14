package co.com.bancolombia.certification.compararjsonvsxml.questions;

import org.w3c.dom.Element;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ValidarDatos implements Question<Boolean> {

	private Element textElement;
	private String valueFieldJSON;
	private String type;

	public ValidarDatos(Element textElement, String valueFieldJSON, String type) {
		this.textElement = textElement;
		this.valueFieldJSON = valueFieldJSON;
		this.type = type;
	}

	@Override
	public Boolean answeredBy(Actor actor) {
		/*
		 * ElementosJson readJSONFile = new ElementosJson();
		 * 
		 * String valueField; //String texto = null; boolean bCorrecto = false;
		 * 
		 * if (textElement.getFirstChild() != null) { valueField =
		 * readJSONFile.formatText(textElement.getFirstChild().getNodeValue().trim());
		 * if (type.equals(ElementosJson.TYPEDATAMULTILINE)) { valueField =
		 * readJSONFile.formatText(textElement.getTextContent().trim()); } if
		 * (valueFieldJSON.equalsIgnoreCase(valueField)) { //texto = "[" +
		 * valueFieldJSON + "] SI es igual a [" + valueField + "]"; bCorrecto = true; }
		 * else { //texto = "[" + valueFieldJSON + "] NO es igual a [" + valueField +
		 * "]"; bCorrecto = false; } } return bCorrecto;
		 */
		return null;
	}

	public String formatText(String valueField) {
		String valueTemp = valueField.replaceAll("\"", "'");
		valueTemp = valueTemp.replaceAll("\t", "");
		valueTemp = valueTemp.replaceAll("\n", "");
		return valueTemp;

		// return null;
	}

	public static ValidarDatos deLosArchivos(Element textElement, String valueFieldJSON, String type) {
		return new ValidarDatos(textElement, valueFieldJSON, type);
	}
}
