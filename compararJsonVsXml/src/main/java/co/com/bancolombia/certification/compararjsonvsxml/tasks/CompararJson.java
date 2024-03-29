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

import co.com.bancolombia.certification.compararjsonvsxml.utils.ArchivosComprimidos;
import co.com.bancolombia.certification.compararjsonvsxml.utils.Checksum;
import co.com.bancolombia.certification.compararjsonvsxml.utils.Descomprime;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ElementosJson;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ElementosXml;
import co.com.bancolombia.certification.compararjsonvsxml.utils.FormatoJson;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ReplaceCharacters;
import co.com.bancolombia.certification.compararjsonvsxml.utils.RutaJson;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ValidarVacio;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CompararJson implements Task {

	public String rutaFileXml;
	public String rutaFileAnexo;
	public String rutaFileJson;
	public String rutaFileLog;
	public String rutaFilePropiedades;
	public String rutaFileXmlHijo;
	private String baseDeDatos;

	public static String rutaFileXmls;
	public static String rutaFileAnexos;
	public static String rutaJsons;
	public static String rutaFileLogs;
	public static String rutaFilePropiedadess;
	public static String rutaFileXmlHijos;

	public CompararJson(String rutaFileXml, String rutaFileAnexo, String rutaFileJson, String rutaFileLog,
			String rutaFilePropiedades, String rutaFileXmlHijo, String baseDeDatos) {
		this.rutaFileXml = rutaFileXml;
		this.rutaFileAnexo = rutaFileAnexo;
		this.rutaFileJson = rutaFileJson;
		this.rutaFileLog = rutaFileLog;
		this.rutaFilePropiedades = rutaFilePropiedades;
		this.rutaFileXmlHijo = rutaFileXmlHijo;
		this.baseDeDatos = baseDeDatos;
		rutaFileXmls = rutaFileXml;
		rutaFileAnexos = rutaFileAnexo;
		rutaJsons = rutaFileJson;
		rutaFileLogs = rutaFileLog;
		rutaFilePropiedadess = rutaFilePropiedades;
		rutaFileXmlHijos = rutaFileXmlHijo;
	}

	RutaJson rutaJson = new RutaJson();
	ArchivosComprimidos archivosComprimidos = new ArchivosComprimidos();

	Descomprime descomprime = new Descomprime();

	ElementosJson elementosJson = new ElementosJson();

	@Override
	public <T extends Actor> void performAs(T actor) {

		List<String> listaJson = rutaJson.rutaArchivoJson(rutaJsons); // = rutaJson.devolverListaFiles;

		// para rutas compartidas se debe cambiar la variable por la de Anexos
		String route = ReplaceCharacters.of(rutaFileAnexo);
		String rutaTxt = ReplaceCharacters.of(rutaFilePropiedades);
		String routeHijo = ReplaceCharacters.of(rutaFileXmlHijos);
		String routeFolder = "";
		String routeFolderHijo = "";
		Object valueObj;
		Object valueObjHijo;
		Object valueField;
		Object valueFieldHijo;
		String keyField;
		String keyFieldHijo;

		// Variables de informe de datos fallidos
		String sUniversalDamage = "";
		boolean bAnexoExitoso = true;
		boolean bAnexoExitosoHijo = true;
		boolean bTieneHijos = false;
		String sDatosUniversalIdFallidos = "";
		String sCantidadFallidos = "";
		int iContTotalUniversalId = 0;
		int iContUniversalIdExitoso = 0;
		int iXmlVacios = 0;
		String sMensajeTotalUniversalId = "";
		String sMensajeUiversalIdExitosos = "";
		String sLineaSeparadora = "\n" + "============================================================================="
				+ "\n";

		for (String fileNameJSON : listaJson) {

			try {
				// Read JSON
				ObjectMapper mapper = new ObjectMapper();
				File fileJSON = new File(fileNameJSON);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileJSON), "UTF8"));

				String valueLineString = "";
				String[] SpecialCharacters = { "\\x15", "\\x1F", "\\x1c", "\\x11", "\\x12", "\\x13", "\\x14", "\0",
						"\1", "\2", "\3", "\4", "\5", "\6", "\\a", "\\b", "\\t", "\\n", "\\v", "\\f", "\\r", "\\x0e",
						"\\x0f", "\\x10", "\\x15", "\\x16", "\\x17", "\\x18", "\\x19", "\\x1a", "\\x1b", "\\x1c",
						"\\x1d", "\\x1e", "\\x1f" };
				Checksum.readLinesFile(rutaTxt);

				while ((valueLineString = br.readLine()) != null) {

					// Elimna todos los caracteres especiales ASCII que ven gan en el JSON
					valueLineString = FormatoJson.replaceCharacterSpecialJson(valueLineString, SpecialCharacters);

					// Convierte a un JsonOBject cada l�nea le�da
					JSONObject jsonDocument = (JSONObject) new JSONParser().parse(valueLineString);
					Map<Object, Object> documentObj = mapper.convertValue(jsonDocument, Map.class);

					// Extrae el valor del UniversalId del Json
					String universalID = (String) documentObj.get("UniversalID");
					String universalIDCompuesto = (String) documentObj.get("UniversalIDCompuesto");
					// convierte el documento el XML encontrado con el universalId Anterior
					Document xmlDoc = ElementosXml.getDocumentByUniversalId(universalID, false, rutaFileXml,
							rutaFileXmlHijo);

					if (universalID != null) {
						// listUrlUniversalId.add(URL_SERVER+universalID);
						iContTotalUniversalId++;
					} else {
						iContTotalUniversalId = iContTotalUniversalId;
					}

					// Se le asigna el valor de la ruta o direcci�n del anexo donde se encuentra el
					// universalID
					routeFolder = route + universalIDCompuesto;

					// Se le asigna el valor de la ruta o direcci�n de la carpeta de los XML de los
					// hijos donde se encuentra el universalID
					routeFolderHijo = routeHijo + universalIDCompuesto;

					// Descomprime los anexos y los guarda en una lista
					List<String> nameFilesZip = descomprime.listFilesArchivoZip(routeFolder + ".zip",baseDeDatos);

					String estadoVacio = "";
					// Se recibe el estado (vacio, null o false) del ZIP, que indica si est� o no
					// vac�o para saber (si es null o false, se compara) si se compara o no con el
					// Json
					estadoVacio = ValidarVacio.getEstadoNull();

					if (xmlDoc != null) {

						System.out.println("UniversalID: " + universalID);

						for (Map.Entry<Object, Object> field : documentObj.entrySet()) {
							keyField = (String) field.getKey();
							valueObj = field.getValue();

							// Si el Universal ID tiene una �nica etiqueta con una o m�ltiples l�neas
							if (valueObj instanceof LinkedHashMap) {
								bTieneHijos = false;
								Map<Object, Object> fieldValue = mapper.convertValue(valueObj, Map.class);
								valueField = fieldValue.get("value");

								// Si el Universal ID tiene una �nica etiqueta con una l�nea
								if (valueField instanceof String) {

									valueField = ((String) valueField).trim();
									elementosJson.readField(universalID, keyField, (String) valueField, xmlDoc,
											bTieneHijos);

									// Si el Universal ID tiene una �nica etiqueta con m�ltiples l�neas (es un mismo
									// texto)
								} else if (valueField instanceof ArrayList) {
									elementosJson.readField(universalID, keyField, (ArrayList) valueField, xmlDoc,
											bTieneHijos);
								}
								// Si el Universal ID tiene varias etiquetas con una o m�ltiples l�neas
							} else if (valueObj instanceof ArrayList) {

								if (!estadoVacio.equals("vacio") && !keyField.equals("hijos")) {
									// si se entra en esta condici�n, es porque el ZIP no est� vac�o y se puede
									// comparar con el Json, Adem�s, no tiene hijos.
									bAnexoExitoso = (archivosComprimidos.comparateAttachments(
											(ArrayList<String>) valueObj, (ArrayList<String>) nameFilesZip,
											routeFolder + ".zip", universalID, keyField, baseDeDatos));
									System.out.println("bAnexoExitoso " + bAnexoExitoso);
								}

								// Si entra ac�, es porque el Json tiene hijos
								if (keyField.equals("hijos")) {

									ArrayList<Object> objHijo = (ArrayList<Object>) valueObj;
									bTieneHijos = true;
									for (int i = 0; i < objHijo.size(); i++) {

										// mapea los objetos o llave valor de la etiqueta hijos
										Map<Object, Object> documentObjHijo = mapper.convertValue(objHijo.get(i),
												Map.class);
										for (Map.Entry<Object, Object> fieldHijo : documentObjHijo.entrySet()) {

											// Se asigna el valor del UniversalID del hijo para despu�s buscar el XML
											String universalIDHijo = (String) documentObjHijo.get("UniversalID");
											valueObjHijo = fieldHijo.getValue();

											// Empieza a recorrer la etiqueta hijos y todo su contenido
											if (valueObjHijo instanceof LinkedHashMap) {

												// mapea los objetos o llave valor de las etiquetas internas de hijos
												// para empezar a comparar
												Map<Object, Object> fieldValueHijo = mapper.convertValue(valueObjHijo,
														Map.class);

												valueFieldHijo = fieldValueHijo.get("value");
												keyFieldHijo = (String) fieldHijo.getKey();

												// Busca en la ruta donde est�n los XML del hijo y trae su informaci�n
												// para compararla
												Document xmlDocHijo = ElementosXml.getDocumentByUniversalId(
														universalIDHijo, bTieneHijos, rutaFileXml, rutaFileXmlHijo);

												// Empieza a comparar los json con los xml
												if (valueFieldHijo instanceof String) {

													valueField = ((String) valueFieldHijo).trim();
													elementosJson.readField(universalID + " (" + universalIDHijo + ")",
															keyFieldHijo, (String) valueFieldHijo, xmlDocHijo,
															bTieneHijos);

												} else if (valueFieldHijo instanceof ArrayList) {

													elementosJson.readField(universalID + " (" + universalIDHijo + ")",
															keyFieldHijo, (ArrayList) valueFieldHijo, xmlDocHijo,
															bTieneHijos);
												}
											}
										}
									}
								}
							}
						}
					} else {

						System.out.println(".::Comparar Json::. EL sgte XML est� vac�o o no existe: " + universalID);
						iXmlVacios++;
						sUniversalDamage = sUniversalDamage + "\n" + universalID;
					}
					System.gc();
				}
			} catch (FileNotFoundException | IllegalArgumentException | ParseException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				Logger.getLogger(ArchivosComprimidos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		actor.attemptsTo(Guardar.losValores(sMensajeTotalUniversalId, iContTotalUniversalId, sLineaSeparadora,
				sDatosUniversalIdFallidos, bTieneHijos, sCantidadFallidos, iContUniversalIdExitoso,
				sMensajeUiversalIdExitosos, iXmlVacios, sUniversalDamage, rutaFileLogs));

	}

	public static CompararJson conArchivoXml(String rutaFileXml, String rutaFileAnexo, String rutaFileJson,
			String rutaFileLog, String rutaFilePropiedades, String rutaFileXmlHijo, String baseDeDatos) {
		return Tasks.instrumented(CompararJson.class, rutaFileXml, rutaFileAnexo, rutaFileJson, rutaFileLog,
				rutaFilePropiedades, rutaFileXmlHijo, baseDeDatos);
	}

}