package co.com.bancolombia.certification.compararjsonvsxml.utils;

public class ReplaceCharacters {

	public static String of(String word) {
		String wordReplaced = word.replaceAll("'", "");
		return wordReplaced;
	}
}
