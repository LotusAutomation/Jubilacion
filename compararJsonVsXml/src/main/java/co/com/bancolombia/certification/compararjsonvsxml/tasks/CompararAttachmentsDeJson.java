package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co.com.bancolombia.certification.compararjsonvsxml.utils.ArchivosComprimidos;
import co.com.bancolombia.certification.compararjsonvsxml.utils.Checksum;
import co.com.bancolombia.certification.compararjsonvsxml.utils.Descomprime;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ElementosJson;
import co.com.bancolombia.certification.compararjsonvsxml.utils.FormatoJson;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ReplaceCharacters;
import co.com.bancolombia.certification.compararjsonvsxml.utils.RutaJson;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CompararAttachmentsDeJson implements Task {

	public String rutaJsons;
	public String rutaFileAnexo;
	public String rutaFilePropiedades;
	private String rutaFileLogs;
	private String baseDeDatos;
	public static String rutaFileAnexos;
	public static String rutaFilePropiedadess;

	public CompararAttachmentsDeJson(String rutaFileAnexo, String rutaFilePropiedades, String rutaJsons,
			String rutaFileLogs, String baseDeDatos) {
		this.rutaFileAnexo = rutaFileAnexo;
		this.rutaFilePropiedades = rutaFilePropiedades;
		this.rutaFileLogs = rutaFileLogs;
		this.rutaJsons = rutaJsons;
		this.baseDeDatos = baseDeDatos;
		rutaFileAnexos = rutaFileAnexo;
		rutaFilePropiedadess = rutaFilePropiedades;
	}

	RutaJson rutaJson = new RutaJson();
	ArchivosComprimidos archivosComprimidos = new ArchivosComprimidos();
	Descomprime descomprime = new Descomprime();
	ElementosJson elementosJson = new ElementosJson();

	@Override
	public <T extends Actor> void performAs(T actor) {
		List<String> listaJson = rutaJson.rutaArchivoJson(rutaJsons);
		String route = ReplaceCharacters.of(rutaFileAnexos);
		String rutaTxt = ReplaceCharacters.of(rutaFilePropiedadess);
		String routeFolder = "";
		Object valueObj;
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

					// Extrae el valor del UniversalId
					String universalID = (String) documentObj.get("UniversalID");

					if (universalID != null) {
						iContTotalUniversalId++;
					} else {
						iContTotalUniversalId = iContTotalUniversalId;
					}

					routeFolder = route + universalID;
					List<String> nameFilesZip = descomprime.listFilesArchivoZip(routeFolder + ".zip", baseDeDatos);

					System.out.println("UniversalID: " + universalID);

					for (Map.Entry<Object, Object> field : documentObj.entrySet()) {
						keyField = (String) field.getKey();
						valueObj = field.getValue();

						if (valueObj instanceof ArrayList) {
							try {
								if (!keyField.equals("hijos")) {
									bAnexoExitoso = (archivosComprimidos.comparateAttachments(
											(ArrayList<String>) valueObj, (ArrayList<String>) nameFilesZip,
											routeFolder + ".zip", universalID, keyField, baseDeDatos));
									System.out.println("bAnexoExitoso " + bAnexoExitoso);
								}
							} catch (Exception e) {
								System.out.println(".::ERROR::..archivosComprimidos.comparateAttachments: " + e);
							}
						} else {
							System.out
									.println(".::Comparar Json::. EL sgte XML está vacío o no existe: " + universalID);
							iXmlVacios++;
							sUniversalDamage = sUniversalDamage + "\n" + universalID;
						}
						System.gc();
					}
				}
			} catch (FileNotFoundException | IllegalArgumentException | ParseException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				Logger.getLogger(ArchivosComprimidos.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		actor.attemptsTo(Guardar.losValores(sMensajeTotalUniversalId, iContTotalUniversalId, sLineaSeparadora,
				sDatosUniversalIdFallidos, false, sCantidadFallidos, iContUniversalIdExitoso,
				sMensajeUiversalIdExitosos, iXmlVacios, sUniversalDamage, rutaFileLogs));
	}

	public static CompararAttachmentsDeJson conArchivoXml(String rutaFileAnexo, String rutaFilePropiedades,
			String rutaFileJson, String rutaFileLog, String baseDeDatos) {
		return Tasks.instrumented(CompararAttachmentsDeJson.class, rutaFileAnexo, rutaFilePropiedades, rutaFileJson,
				rutaFileLog, baseDeDatos);
	}
}