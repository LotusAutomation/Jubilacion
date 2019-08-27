package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Checksum {
	private static HashMap<String, String> hash_map = new HashMap<String, String>();

	public static void comparateChecksum(String filepath, String keyField) {
		MessageDigest md5;
		StringBuilder result = null;
		String[] arrUniversalId = null;
		String UniversalId = "";
		String separador = "";
		try {
			md5 = MessageDigest.getInstance("MD5");
			// DigestInputStream is better, but you also can hash file like this.
			try (InputStream fis = new FileInputStream(filepath)) {
				byte[] buffer = new byte[1024];
				int nread;
				while ((nread = fis.read(buffer)) != -1) {
					md5.update(buffer, 0, nread);
				}
			}
			// bytes to hex
			result = new StringBuilder();
			for (byte b : md5.digest()) {
				result.append(String.format("%02x", b));
			}
		} catch (Exception e) {
			System.out.println(".::ERROR::.checksum " + e);
		}
		separador = Pattern.quote("\\");
		arrUniversalId = filepath.split(separador);
		UniversalId = arrUniversalId[((arrUniversalId.length) - 1)];
		System.out.println("searchUniversalId::.. " + UniversalId);
		extractFileMD5(result.toString(), UniversalId, keyField);
	}
	
	public static void readLinesFile(String path) {
		// Mapa para almacenar todo lo contenido en el archivo .txt
		BufferedReader reader;
		String line = "";
		String separador = "";
		try {			
			reader = new BufferedReader(new FileReader(path));
			while (reader != null) {
				// System.out.println(line);
				// read next line
				line = reader.readLine();
					if(line != null) {				
				//	System.out.println("ERRORread::. " + line);
					// Leer el archivo de anexos y
					separador = Pattern.quote("|");
					String[] arrOnlyFileMD5 = line.split(separador);
					// Llenar el mapa con los datos del .txt de anexos
					// Mapping string values to int keys
					hash_map.put(arrOnlyFileMD5[0], arrOnlyFileMD5[1]);
						}else {
							reader.close();
						}
			}	
		//	resultExtracMD5 = extractFileMD5(hash_map, ChecksumJson, UniversalId);
		} catch (IOException e) {
			System.out.println(".::ERRORreadLinesFile::. " + e);
		}		
	}

	
/*
	public static boolean readLinesFile(String path, String ChecksumJson, String UniversalId) {
		// Mapa para almacenar todo lo contenido en el archivo .txt
		HashMap<String, String> hash_map = new HashMap<String, String>();
		BufferedReader reader;
		String line = "";
		String separador = "";
		try {			
			reader = new BufferedReader(new FileReader(path));
			while (reader != null) {
				// System.out.println(line);
				// read next line
				line = reader.readLine();
					if(line != null) {				
				//	System.out.println("ERRORread::. " + line);
					// Leer el archivo de anexos y
					separador = Pattern.quote("|");
					String[] arrOnlyFileMD5 = line.split(separador);
					// Llenar el mapa con los datos del .txt de anexos
					// Mapping string values to int keys
					hash_map.put(arrOnlyFileMD5[0], arrOnlyFileMD5[1]);
						}else {
							reader.close();
						}
			}	
		//	resultExtracMD5 = extractFileMD5(hash_map, ChecksumJson, UniversalId);
		} catch (IOException e) {
			System.out.println(".::ERRORreadLinesFile::. " + e);
		}		
		return extractFileMD5(hash_map, ChecksumJson, UniversalId);
	}
*/
	public static void extractFileMD5(String checksumJson, String searchUniversalId, String keyField) {
		String md5JSON = "";
		String md5XML = "";
		boolean checkSumCheck;
		try { // traer el valor de cada checksum del arreglo de anexos,
				// para compararlo con el que se genere en el método Checksum
			md5XML = hash_map.get(searchUniversalId);
			// el método debe retornar el chechum apartir del la ruta del zip indicado
			md5JSON = checksumJson;
			
			System.out.println("extractFileMD5::. " + md5JSON);
			System.out.println("extractXMLMD5::.  " + md5XML);
			checkSumCheck = (md5JSON.trim().equals(md5XML.trim()));
			System.out.println("checkSumCheck::. " + checkSumCheck);

			
			if (checkSumCheck == false) {
				// ------------Escribir en Archivo de evidencia----------------
				String sMensajeAttachments = "[ " + searchUniversalId + " ] CheckSum XML [ " + md5XML   + " ] DIFERENTE DE JSON [ " + md5JSON +" ]";
				ListaDeFallidos.validarFallidos("fallido$-" + md5XML, searchUniversalId, keyField, md5JSON);
				// ------------Escribir en Archivo de evidencia---------------
					}
			
		} catch (Exception e) {
			System.out.println(".::ERROR::.algoArra " + e);
		}

	}

}
