package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RutaJson {

	public RutaJson() {
		super();
	}

	// devolver una lista de archivo List<File>

	public static List<String> rutaArchivoJson(String rutaJsons) {

		// Se asigna el valor de la ruta de los JsonFile
		// y se eliminan las comillas simples
		String path = "";
		path = ReplaceCharacters.of(rutaJsons);

		String pathOut = "";
		List<String> ListPath = new ArrayList<String>();

		String files = "";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		Integer iContador = 0;

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();

				if (files.endsWith(".json")) {
					pathOut = path + files;
					// pathOut = pathGZ + files;
					ListPath.add(pathOut);

					iContador++;
					System.out.println(files + " en la ruta " + pathOut);
				}
			}
		}
		System.out.println("+++++++++++++++++++++++++" + ListPath.size());
		System.out.println("En la ruta hay: [" + iContador + "] .Json" + "Fin");
		// devolver una lista de archivo List<File>

		return ListPath;
	}

}
