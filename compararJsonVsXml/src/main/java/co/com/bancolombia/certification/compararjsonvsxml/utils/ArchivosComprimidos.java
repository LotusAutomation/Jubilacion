package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ArchivosComprimidos {

	FormatosCampos formatosCampos = new FormatosCampos();

	public static void delete(File file) {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			file.delete();
		}
	}

	public boolean comparateAttachments(ArrayList<String> filesJSON, ArrayList<String> filesFolder, String filepath,
			String universalID, String keyField, String baseDeDatos) {

		ArrayList<String> arrFilesFolder = new ArrayList<String>();
		int contJ = 0;
		int contF = 0;
		boolean attachmentCheck = false;
		boolean universalID_PDF_Obligatorio = false;
		String sValorAttachmentJson, sValorAttachmentXml = "";

		// Si hay algun archivo adjunto en el JSon
		if (filesJSON.size() > 0) {
			String listFileJSON[] = null;
			filesJSON = formatosCampos.originalArray(filesJSON);
			listFileJSON = new String[filesJSON.size()];

			// Recorre la lista de archivos adjuntos en el Json y almacena su nombre en una
			// lista de String para despues compararla con el XML
			for (int j = 0; j < filesJSON.size(); j++) {
				contJ++;
				filesJSON.set(j, formatosCampos.extractOnlyFileName(filesJSON.get(j)));

				// Si la BD es de COMEX, me consulte si en el JSon hay un adjunto con nombre
				// UniversalID.pdf, el cual siempre debe estar cuando se trabaja con COMEX
				if (baseDeDatos.equals("COMEX")) {
					if ((filesJSON.get(j).contains(universalID + ".pdf"))
							|| (filesJSON.get(j).contains(universalID + ".PDF"))) {
						universalID_PDF_Obligatorio = true;
					}
				}
				listFileJSON[j] = filesJSON.get(j);
			}

			// Recorre la lista de archivos adjuntos en el XML y almacena su nombre en un
			// arrayList de String para despues compararlo con los adjuntos en el JSon
			for (int i = 0; i < filesFolder.size(); i++) {
				contF++;
				filesFolder.set(i, formatosCampos.extractOnlyFileName(filesFolder.get(i)));
				arrFilesFolder.add(filesFolder.get(i));
			}

			// Decimos que si la BD es de COMEX, pregunte si encontro el adjunto con nombre
			// UniversalID.pdf. Si es de COMEX y no lo encontro, me lo guarde como un
			// UniversalID fallido. Tambien se validan si los demas adjuntos existen en el
			// JSon vs XML.
			if (baseDeDatos.equals("COMEX")) {
				attachmentCheck = filesJSON.containsAll(filesFolder)
						&& arrFilesFolder.containsAll(Arrays.asList(listFileJSON))
						&& (universalID_PDF_Obligatorio == true);
			} else {
				// Cuando es de otra BD diferente de COMEX, valide si los adjuntos existen en el
				// JSon vs XML.
				attachmentCheck = filesJSON.containsAll(filesFolder)
						&& arrFilesFolder.containsAll(Arrays.asList(listFileJSON));
			}

			// -------------------------------------------------------------
			// Guardar los UNIVERSAL_ID fallidos para despues mostrarlos en el reporte

			if (attachmentCheck == false) {

				sValorAttachmentJson = filesJSON.toString();
				sValorAttachmentXml = filesFolder.toString();

				if (universalID_PDF_Obligatorio == false) {
					// Escribir en la lista de fallidos,que después irá a ResumenPruebasXXX.log
					ListaDeFallidos.validarFallidos("fallido$-" + sValorAttachmentXml, universalID, keyField,
							sValorAttachmentJson + "  No se encontro UniversalID.pdf en el Json.", false);
				} else {
					// Escribir en la lista de fallidos,que después irá a ResumenPruebasXXX.log
					ListaDeFallidos.validarFallidos("fallido$-" + sValorAttachmentXml, universalID, keyField,
							sValorAttachmentJson, false);
				}

			}

		} else if (baseDeDatos.equals("COMEX")) {
			String vec[] = new String[1];
			vec[0] = "No hay attachment UniversalID.pdf en el JSon.";
			// Escribir en la lista de fallidos,que después irá a ResumenPruebasXXX.log
			ListaDeFallidos.validarFallidos(
					"fallido$-" + sValorAttachmentXml + " No hay attachment UniversalID.pdf en el XML", universalID,
					keyField, vec[0], false);

		}

		Checksum.comparateChecksum(filepath, keyField);
		return attachmentCheck;
	}

	public String[] getNamesFiles(String routeFiles) {

		File folder = new File(routeFiles);
		File[] listOfFiles = folder.listFiles();
		String name = "";
		String names[] = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			name = listOfFiles[i].getName();
			names[i] = name;
		}
		return names;
	}

}
