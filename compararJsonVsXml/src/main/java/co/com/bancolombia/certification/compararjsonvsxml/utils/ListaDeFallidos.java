package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.util.ArrayList;

public class ListaDeFallidos {

	public static ArrayList<DatosFallidos> objetoCampoFallido = new ArrayList<DatosFallidos>();
	
	public static void retornarListaFallidos(String universalId, String nombreCampo, String valorCampoJson, String valorCampoXml) {
		DatosFallidos[] oDatosFallidos = new DatosFallidos[5000000];
		
			oDatosFallidos[ElementosJson.iContador]=new DatosFallidos(universalId, nombreCampo, valorCampoJson, valorCampoXml);
			
			objetoCampoFallido.add(oDatosFallidos[ElementosJson.iContador]);
			
			ElementosJson.iContador++;
			
	}
	
	public static void validarFallidos(String resultado, String universalID, String keyField, String valueField) {

		if (resultado.substring(0, 7).equalsIgnoreCase("fallido")) {
			String[] valorCampoXml = resultado.split("\\$-");
			retornarListaFallidos(universalID, keyField, valueField, valorCampoXml[1]);
		}

	}
}
