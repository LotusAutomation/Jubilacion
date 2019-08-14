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
import co.com.bancolombia.certification.compararjsonvsxml.utils.ListaDeFallidos;
import co.com.bancolombia.certification.compararjsonvsxml.utils.Logs;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ReplaceCharacters;
import co.com.bancolombia.certification.compararjsonvsxml.utils.RutaComprimidos;
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

	public static String rutaFileXmls;
	public static String rutaFileAnexos;
	public static String rutaJsons;
	public static String rutaFileLogs;
	public static String rutaFilePropiedadess;
	public static String rutaFileXmlHijos;

	public CompararJson(String rutaFileXml, String rutaFileAnexo, String rutaFileJson, String rutaFileLog,
			String rutaFilePropiedades, String rutaFileXmlHijo) {
		this.rutaFileXml = rutaFileXml;
		this.rutaFileAnexo = rutaFileAnexo;
		this.rutaFileJson = rutaFileJson;
		this.rutaFileLog = rutaFileLog;
		this.rutaFilePropiedades = rutaFilePropiedades;
		this.rutaFileXmlHijo = rutaFileXmlHijo;
		rutaFileXmls = rutaFileXml;
		rutaFileAnexos = rutaFileAnexo;
		rutaJsons = rutaFileJson;
		rutaFileLogs = rutaFileLog;
		rutaFilePropiedadess = rutaFilePropiedades;
		rutaFileXmlHijos = rutaFileXmlHijo;
	}

	RutaJson rutaJson = new RutaJson();
	RutaComprimidos rutaComprimidos = new RutaComprimidos();
	ArchivosComprimidos archivosComprimidos = new ArchivosComprimidos();

	Descomprime descomprime = new Descomprime();

	// private String fileNameJSON;
	ElementosJson elementosJson = new ElementosJson();

	@Override
	public <T extends Actor> void performAs(T actor) {

		List<String> listaJson = rutaJson.rutaArchivoJson(); // = rutaJson.devolverListaFiles;

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

		// String fileNameJSON
		// ="D:\\Datos_Prueba\\LOTUS_RECLAMOS_RECLAMOS_20190410_036_3.json";
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

					// Convierte a un JsonOBject cada línea leída
					JSONObject jsonDocument = (JSONObject) new JSONParser().parse(valueLineString);
					Map<Object, Object> documentObj = mapper.convertValue(jsonDocument, Map.class);

					// Extrae el valor del UniversalId del Json
					String universalID = (String) documentObj.get("UniversalID");
					// convierte el documento el XML encontrado con el universalId Anterior
					Document xmlDoc = ElementosXml.getDocumentByUniversalId(universalID, false);

//					Document xmlDocHijo = ElementosXmlHijos.getDocumentByUniversalId(universaIDHijo);

					if (universalID != null) {
						// listUrlUniversalId.add(URL_SERVER+universalID);
						iContTotalUniversalId++;
					} else {
						iContTotalUniversalId = iContTotalUniversalId;
					}

					routeFolder = route + universalID;
					routeFolderHijo = routeHijo + universalID;
					/*
					 * descomprime.archivoZip(routeFolder + ".zip", routeFolder); String[]
					 * filesFolder = archivosComprimidos.getNamesFiles(routeFolder); File directory
					 * = new File(routeFolder); archivosComprimidos.delete(directory);
					 */

					List<String> nameFilesZip = descomprime.listFilesArchivoZip(routeFolder + ".zip");

					String estadoVacio = "";
					// Se recibe el estado (vacio, null o false) del ZIP, que indica si está o no
					// vacío para saber (si es null o false, se compara) si se compara o no con el
					// Json
					estadoVacio = ValidarVacio.getEstadoNull();

					if (xmlDoc != null) {

						System.out.println("UniversalID: " + universalID);

						for (Map.Entry<Object, Object> field : documentObj.entrySet()) {
							keyField = (String) field.getKey();
							valueObj = field.getValue();

							// System.out.println("Key: " + keyField);
							if (valueObj instanceof LinkedHashMap) {
								bTieneHijos = false;
								Map<Object, Object> fieldValue = mapper.convertValue(valueObj, Map.class);
								valueField = fieldValue.get("value");

								if (valueField instanceof String) {

									valueField = ((String) valueField).trim();
									elementosJson.readField(universalID, keyField, (String) valueField, xmlDoc,
											bTieneHijos);

								} else if (valueField instanceof ArrayList) {
									elementosJson.readField(universalID, keyField, (ArrayList) valueField, xmlDoc,
											bTieneHijos);
								}
							} else if (valueObj instanceof ArrayList) {

								if (!estadoVacio.equals("vacio") && !keyField.equals("hijos")) {
									// si se entra en esta condición, es porque el ZIP no está vacío y se puede
									// comparar con el Json
									bAnexoExitoso = (archivosComprimidos.comparateAttachments(
											(ArrayList<String>) valueObj, (ArrayList<String>) nameFilesZip,
											routeFolder + ".zip", universalID, keyField));
									System.out.println("bAnexoExitoso " + bAnexoExitoso);
								}

								// } if diferente de hijos
								if (keyField.equals("hijos")) {
									ArrayList<Object> objHijo = (ArrayList<Object>) valueObj;
									bTieneHijos = true;
									for (int i = 0; i < objHijo.size(); i++) {

										// mapea los objetos o llave valor de la etiqueta hijos
										Map<Object, Object> documentObjHijo = mapper.convertValue(objHijo.get(i),
												Map.class);
										for (Map.Entry<Object, Object> fieldHijo : documentObjHijo.entrySet()) {

											// Se asigna el valor del UniversalID del hijo para después buscar el XML
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

												// Busca en la ruta donde están los XML del hijo y trae su información
												// para compararla
												Document xmlDocHijo = ElementosXml
														.getDocumentByUniversalId(universalIDHijo, bTieneHijos);

												// Empieza a comparar los json con los xml
												if (valueFieldHijo instanceof String) {
													valueField = ((String) valueFieldHijo).trim();
													elementosJson.readField(universalID + " (" + universalIDHijo + ")",
															keyFieldHijo, (String) valueFieldHijo, xmlDocHijo,
															bTieneHijos);
													// universalID + " (" + universalIDHijo+")"
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
						System.out.println(".::Comparar Json::. EL sgte XML está vacío o no existe: " + universalID);
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
			// System.out.println("==============================================================");
			// System.out.println("Las rutas de los Json es: " + fileNameJSON);

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
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsValorCampoXml()
					+ "|  ---ES DIFERENTE DE:---  DatoJson: |"
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
					// System.out.println(idInicial);
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

		ListaDeFallidos.objetoCampoFallido.clear();

		if (bTieneHijos) {
			// ------Escribir en el Log la cantidad de fallidos si tiene hijos------

			sCantidadFallidos = sLineaSeparadora + "\n" + "La cantidad de Universal Id Fallidos es de: [ "
					+ ElementosJson.iContador + " ]" + "\n" + sLineaSeparadora;
			System.out.println(sCantidadFallidos);
			Logs.writeFile(sCantidadFallidos);

			iContUniversalIdExitoso = iContTotalUniversalId - ElementosJson.iContador;

			// ------Escribir en el Log la cantidad de fallidos si tiene hijos------

		} else {
			// --------Escribir en el Log la cantidad de fallidos sin hijos------
			sCantidadFallidos = sLineaSeparadora + "\n" + "La cantidad de Universal Id Fallidos es de: [ " + fallidos
					+ " ]" + "\n" + sLineaSeparadora;
			System.out.println(sCantidadFallidos);
			Logs.writeFile(sCantidadFallidos);

			iContUniversalIdExitoso = iContTotalUniversalId - fallidos;
			// ------Escribir en el Log la cantidad de fallidos sin hijos-------
		}

		sMensajeUiversalIdExitosos = sLineaSeparadora + "\n"
				+ " La cantidad de Universal Id exitosos validados es de: [ " + iContUniversalIdExitoso + " ] "
				+ sLineaSeparadora;
		Logs.writeFile(sMensajeUiversalIdExitosos);

		// -------------------Construción de informa---------------------------
		System.out.println("==============================================================");
		System.out.println("Cantidad de Universal Id validados es: " + iContTotalUniversalId);

		System.out.println("==============================================================");
		System.out.println(
				"Cantidad de Xml Vacíos o inexistentes: " + iXmlVacios + " \n Son: " + sUniversalDamage + " \n");
		System.out.println("==============================================================");

		System.out.println("==============================================================");
		System.out.println("Cantidad de .ZIP Vacíos o inexistentes: " + Descomprime.iCantidadZipErrados + " \n Son: \n"
				+ Descomprime.sZipErrados + " \n");
		System.out.println("==============================================================");
	}

	public static CompararJson conArchivoXml(String rutaFileXml, String rutaFileAnexo, String rutaFileJson,
			String rutaFileLog, String rutaFilePropiedades, String rutaFileXmlHijo) {
		return Tasks.instrumented(CompararJson.class, rutaFileXml, rutaFileAnexo, rutaFileJson, rutaFileLog,
				rutaFilePropiedades, rutaFileXmlHijo);
	}

}