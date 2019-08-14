package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ListaUrlLotusServer {

	public static List<String> porCadaUniversalId() {
		List<String> listUrlUniversalId = new ArrayList<String>();
		RutaJson rutaJson = new RutaJson();

		List<String> listaJson = rutaJson.rutaArchivoJson();

		String universalID;
		long iContTotalUniversalId = 0;

		for (String fileNameJSON : listaJson) {

			try {
				// Read JSON
				ObjectMapper mapper = new ObjectMapper();
				File fileJSON = new File(fileNameJSON);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileJSON), "UTF8"));

				String valueLineString = null;
				while ((valueLineString = br.readLine()) != null) {

					JSONObject jsonDocument = (JSONObject) new JSONParser().parse(valueLineString.trim());
					Map<Object, Object> documentObj = mapper.convertValue(jsonDocument, Map.class);
					universalID = (String) documentObj.get("UniversalID");

					if (universalID != null) {
						iContTotalUniversalId++;
					} else {
						iContTotalUniversalId = iContTotalUniversalId;
					}

				}
			} catch (IllegalArgumentException | ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("El total se Universal Id Encontrados es: [ " + iContTotalUniversalId + " ]");
		return listUrlUniversalId;

	}

	public static void mostrarListaUrls() {
		List<String> listaUrls = porCadaUniversalId();

		for (String sUrls : listaUrls) {
			System.out.println("Lista de Urls" + sUrls);
		}

	}

}
