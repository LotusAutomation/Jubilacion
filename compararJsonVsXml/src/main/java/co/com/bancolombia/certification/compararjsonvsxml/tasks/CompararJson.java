package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;

import co.com.bancolombia.certification.compararjsonvsxml.utils.*;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CompararJson implements Task {

//	public static List<String> listUrlUniversalId = new ArrayList<String>();;
	RutaJson rutaJson = new RutaJson();
	RutaComprimidos rutaComprimidos = new RutaComprimidos();
	ArchivosComprimidos archivosComprimidos = new ArchivosComprimidos();

	Descomprime descomprime = new Descomprime();

	// private String fileNameJSON;
	ElementosJson elementosJson = new ElementosJson();

	@Override
	public <T extends Actor> void performAs(T actor) {

		List<String> listaJson = rutaJson.rutaArchivoJson(); // = rutaJson.devolverListaFiles;

		//para rutas compartidas se debe cambiar la variable por la de Anexos
		//SerenityProperties.sPATH_ANEXO_FILE
		String route = SerenityProperties.sPATH_ANEXO_FILE;
		String rutaTxt = SerenityProperties.sPATH_TXT_FILE;
		String routeFolder = "";
		Object valueObj;
		Object valueField;
		String keyField;

		// Variables de informe de datos fallidos
		String sUniversalDamage = "";
		boolean bAnexoExitoso = true;
		String sDatosUniversalIdFallidos = "";
		String sCantidadFallidos = "";
		int iContTotalUniversalId = 0;
		int iContUniversalIdExitoso = 0;
		int iXmlVacios = 0;
		String sMensajeTotalUniversalId = "";
		String sMensajeUiversalIdExitosos = "";
		String sLineaSeparadora = "\n" + "============================================================================="
				+ "\n";

		// String fileNameJSON ="D:\\Datos_Prueba\\LOTUS_RECLAMOS_RECLAMOS_20190410_036_3.json";
		for (String fileNameJSON : listaJson) {

			try {
				// Read JSON
				ObjectMapper mapper = new ObjectMapper();
				File fileJSON = new File(fileNameJSON);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileJSON), "UTF8"));

				String valueLineString = "";
				String [] SpecialCharacters = {"\\x15" , "\\x1F" , "\\x1c" , "\\x11" , "\\x12" , "\\x13" , "\\x14" , "\0" , "\1" , "\2" , "\3" , "\4" , "\5" , "\6" , "\\a" , "\\b" , "\\t" , "\\n" , "\\v" , "\\f" , "\\r" , "\\x0e" , "\\x0f" , "\\x10" , "\\x15" , "\\x16" , "\\x17" , "\\x18" , "\\x19" , "\\x1a" , "\\x1b" , "\\x1c" , "\\x1d" , "\\x1e" , "\\x1f"};
				Checksum.readLinesFile(rutaTxt);
				
				while ((valueLineString = br.readLine()) != null) {
					//Elimna todos los caracteres especiales ASCII que ven gan en el JSON
					valueLineString = FormatoJson.replaceCharacterSpecialJson(valueLineString, SpecialCharacters );
					//Convierte a un JsonOBject cada línea leída
					JSONObject jsonDocument = (JSONObject) new JSONParser().parse(valueLineString);
					Map<Object, Object> documentObj = mapper.convertValue(jsonDocument, Map.class);
					
					//Extrae el valor del UniversalId 
					String universalID = (String) documentObj.get("UniversalID");
					Document xmlDoc = ElementosXml.getDocumentByUniversalId(universalID);

					
					if (universalID != null) {
					//	listUrlUniversalId.add(URL_SERVER+universalID); 
						iContTotalUniversalId++;
					} else {
						iContTotalUniversalId = iContTotalUniversalId;
					}

					routeFolder = route + universalID;
				/*	descomprime.archivoZip(routeFolder + ".zip", routeFolder);
					String[] filesFolder = archivosComprimidos.getNamesFiles(routeFolder);
					File directory = new File(routeFolder);
					archivosComprimidos.delete(directory);*/
					
					List<String> nameFilesZip = descomprime.listFilesArchivoZip(routeFolder + ".zip");

					if (xmlDoc!=null) {				
						
						System.out.println("UniversalID: " + universalID);
						
						for (Map.Entry<Object, Object> field : documentObj.entrySet()) {
							keyField = (String) field.getKey();
							valueObj = field.getValue();
							//System.out.println("Key: " + keyField);
							if (valueObj instanceof LinkedHashMap) {
								Map<Object, Object> fieldValue = mapper.convertValue(valueObj, Map.class);
								valueField = fieldValue.get("value");
								if (valueField instanceof String) {
									valueField = ((String) valueField).trim();
									elementosJson.readField(universalID, keyField, (String) valueField, xmlDoc);
								} else if (valueField instanceof ArrayList) {
									elementosJson.readField(universalID, keyField, (ArrayList) valueField, xmlDoc);
								}
							} else if (valueObj instanceof ArrayList) {
									if (!keyField.equals("hijos")) {	
										bAnexoExitoso = (archivosComprimidos.comparateAttachments((ArrayList<String>) valueObj,	(ArrayList<String>) nameFilesZip, routeFolder + ".zip", universalID, keyField));
										System.out.println("bAnexoExitoso " + bAnexoExitoso);						
									}							
							}						
						}
					}else {
						System.out.println(".::Comparar Json::. EL sgte XML está vacío o no existe: " + universalID);
						iXmlVacios++;
						sUniversalDamage = sUniversalDamage + "\n" +universalID;
					}
					System.gc();
				}
			} catch (FileNotFoundException | IllegalArgumentException | ParseException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				Logger.getLogger(ArchivosComprimidos.class.getName()).log(Level.SEVERE, null, ex);
			}
		//	System.out.println("==============================================================");
//			System.out.println("Las rutas de los Json es: " + fileNameJSON);

		}

		// -------------------Construcción de información---------------------------
		int fallidos = 0;

		sMensajeTotalUniversalId = "El total de Universal Id validados es de [ " + iContTotalUniversalId + " ]";
		Logs.writeFile(sLineaSeparadora + sMensajeTotalUniversalId + "\n" + sLineaSeparadora);
		
		if (ListaDeFallidos.objetoCampoFallido.size() != 0) {
			fallidos = 1;

			String idInicial = ListaDeFallidos.objetoCampoFallido.get(0).getsUniversaID();
			
			sDatosUniversalIdFallidos = ("UniversalId fallido: [ " + idInicial + " ] " + "Campo: ["
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsNombreCampo() + "],  DatoXML: "
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsValorCampoXml() + "|  ---ES DIFERENTE DE:---  DatoJson: |"
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsValorCampoJson());
		
			Logs.writeFile(sDatosUniversalIdFallidos);

			for (int x = 1; x < ListaDeFallidos.objetoCampoFallido.size(); x++) {

				if (idInicial.equals(ListaDeFallidos.objetoCampoFallido.get(x).getsUniversaID())) {

					sDatosUniversalIdFallidos = ("UniversalId fallido: [ " + idInicial + " ] " + "Campo: ["
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsNombreCampo() + "],  DatoXML: "
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoXml()
							+ "|  ---ES DIFERENTE DE:---  DatoJson: |"
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoJson());

					Logs.writeFile(sDatosUniversalIdFallidos);

				} else {

					idInicial = ListaDeFallidos.objetoCampoFallido.get(x).getsUniversaID();
					//System.out.println(idInicial);
					sDatosUniversalIdFallidos = ("UniversalId fallido: [ " + idInicial + " ] " + "Campo: [" + " "
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsNombreCampo() + "],  DatoXML: "
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoXml()
							+ "|  ---ES DIFERENTE DE:---  DatoJson: |"
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoJson());

					fallidos++;

					Logs.writeFile(sDatosUniversalIdFallidos);

				}

			}

		}
		sCantidadFallidos = sLineaSeparadora + "\n" + "La cantidad de Universal Id Fallidos es de: [ " + fallidos
				+ " ]" + "\n" + sLineaSeparadora;
		System.out.println(sCantidadFallidos);
		Logs.writeFile(sCantidadFallidos);
		
		iContUniversalIdExitoso = iContTotalUniversalId - fallidos;
		
		sMensajeUiversalIdExitosos = sLineaSeparadora + "\n"
				+ " La cantidad de Universal Id exitosos validados es de: [ " + iContUniversalIdExitoso + " ] "
				+ sLineaSeparadora;
		Logs.writeFile(sMensajeUiversalIdExitosos);
		

		// -------------------Construción de informa---------------------------
		System.out.println("==============================================================");
		System.out.println("Cantidad de Universal Id validados es: " + iContTotalUniversalId);

		System.out.println("==============================================================");
		System.out.println("Cantidad de Xml Vacíos o inexistentes: " + iXmlVacios + " \n Son: " +  sUniversalDamage + " \n");
		System.out.println("==============================================================");

		System.out.println("==============================================================");
		System.out.println("Cantidad de .ZIP Vacíos o inexistentes: " + Descomprime.iCantidadZipErrados + " \n Son: \n" +  Descomprime.sZipErrados + " \n");
		System.out.println("==============================================================");
	}

	public static CompararJson conArchivoXml() {
		return Tasks.instrumented(CompararJson.class);
	}

}