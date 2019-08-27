package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ArchivosComprimidos {

	public ArchivosComprimidos() {
		super();
	}

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
			String universalID, String keyField) {

		ArrayList<String> arrFilesFolder = new ArrayList<String>();
		int contJ = 0;
		int contF = 0;
		boolean attachmentCheck = false;
		String sValorAttachmentJson, sValorAttachmentXml = "";

		if (filesJSON.size() > 0) {
			String listFileJSON[] = null;
			filesJSON = formatosCampos.originalArray(filesJSON);
			listFileJSON = new String[filesJSON.size()];

			for (int j = 0; j < filesJSON.size(); j++) {
				contJ++;
				filesJSON.set(j, formatosCampos.extractOnlyFileName(filesJSON.get(j)));
				listFileJSON[j] = filesJSON.get(j);
				System.out.println("filesJSON::. " + filesJSON.get(j) + " \n " + contJ);
			}

			for (int i = 0; i < filesFolder.size(); i++) {
				contF++;
				filesFolder.set(i, formatosCampos.extractOnlyFileName(filesFolder.get(i)));
				arrFilesFolder.add(filesFolder.get(i));
				System.out.println(".::filesFolder::. " + filesFolder.get(i) + " \n " + contF);
			}
			attachmentCheck = filesJSON.containsAll(filesFolder)
					&& arrFilesFolder.containsAll(Arrays.asList(listFileJSON));

		}

		if (attachmentCheck == false) {

			sValorAttachmentJson = filesJSON.toString();
			sValorAttachmentXml = filesFolder.toString();

			// Escribir en la lista de fallidos,que después irá a ResumenPruebasXXX.log
			String sMensajeAttachments = "[ " + universalID + " ] Attachment XML [ " + sValorAttachmentXml
					+ " ] DIFERENTE DE JSON [ " + sValorAttachmentJson + " ]";
			ListaDeFallidos.validarFallidos("fallido$-" + sValorAttachmentXml, universalID, keyField,
					sValorAttachmentJson, false);

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
