package co.com.bancolombia.certification.compararjsonvsxml.tasks;

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


import co.com.bancolombia.certification.compararjsonvsxml.utils.RutaJson;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class DescargarXml implements Task{
	
	public static List<String> listUrlUniversalId = new ArrayList<String>();;
		
	RutaJson rutaJson = new RutaJson();
	
	@Override
	public <T extends Actor> void performAs(T actor) {

		List<String> listaJson = rutaJson.rutaArchivoJson(); // = rutaJson.devolverListaFiles;

		String urlServer = "http://192.168.1.251/DATABASE/BANCOLOMBIA/DESARROLLO/JUBILACION/jubilacion.nsf/agXMLByUnid?Open&unidDB=557362870675F51E0525837C001BA05D&unidDoc=";

		String universalID;
		Integer iContTotalUniversalId = 0;
		
		for (String fileNameJSON : listaJson) {

			try {
				// Read JSON
				ObjectMapper mapper = new ObjectMapper();
				File fileJSON = new File(fileNameJSON);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileJSON), "UTF8"));

				String valueLineString = null;

				while ((valueLineString = br.readLine()) != null) {
					JSONObject jsonDocument = (JSONObject) new JSONParser().parse(valueLineString);
					Map<Object, Object> documentObj = mapper.convertValue(jsonDocument, Map.class);
					universalID = (String) documentObj.get("UniversalID");

					if (universalID != null) {
						listUrlUniversalId.add(urlServer+universalID); 
						iContTotalUniversalId++;
					} else {
						iContTotalUniversalId = iContTotalUniversalId;
					}
		
	}
			}
			catch (IllegalArgumentException | ParseException | IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static DescargarXml delServidorLotus() {
		return Tasks.instrumented(DescargarXml.class);
	}
	
}
