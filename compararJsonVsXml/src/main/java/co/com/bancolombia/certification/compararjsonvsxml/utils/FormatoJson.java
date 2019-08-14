package co.com.bancolombia.certification.compararjsonvsxml.utils;

public class FormatoJson {

	public static String replaceCharacterSpecialJson(String valueLineString, String[] CaractSpecial) {

		String lineRefine = valueLineString;

		for (int i = 0; i < CaractSpecial.length - 1; i++) {
			lineRefine = lineRefine.replaceAll(CaractSpecial[i], "");
		}
		return lineRefine;
	}

}
