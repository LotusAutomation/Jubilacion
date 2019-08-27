package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.util.ArrayList;

public class ListaDeFallidos {

	public static ArrayList<DatosFallidos> objetoCampoFallido = new ArrayList<DatosFallidos>();
	public static Integer iHijo = 0;

	public static void retornarListaFallidos(String universalId, String nombreCampo, String valorCampoJson,
			String valorCampoXml, boolean esHijo) {
		DatosFallidos[] oDatosFallidos = new DatosFallidos[5000000];

		if (esHijo == true) {
			iHijo++;
		} else {
			ElementosJson.iContador++;
		}

		oDatosFallidos[ElementosJson.iContador] = new DatosFallidos(universalId, nombreCampo, valorCampoJson,
				valorCampoXml);

		objetoCampoFallido.add(oDatosFallidos[ElementosJson.iContador]);

	}

	public static void validarFallidos(String resultado, String universalID, String keyField, String valueField,
			boolean esHijo) {

		if (resultado.substring(0, 7).equalsIgnoreCase("fallido")) {
			String[] valorCampoXml = resultado.split("\\$-");
			retornarListaFallidos(universalID, keyField, valueField, valorCampoXml[1], esHijo);
		}

	}

}
