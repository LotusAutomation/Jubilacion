package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RutaComprimidos {

	public RutaComprimidos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static List<String> rutaZipComprimidos() {
		// Aquí la carpeta que queremos explorar
		String path = "C:\\Users\\QVISION\\Documents\\Datos_Prueba\\";
		String pathOut = "";
		List<String> ListPath = new ArrayList<String>();

		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		Integer iContador = 0;

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".zip")) {
					pathOut = path + files;
					ListPath.add(pathOut);

					iContador++;
					// System.out.println(files + " en la ruta " + pathOut);
				}
			}
		}
		// System.out.println("+++++++++++++++++++++++++" +ListPath.size());
		// System.out.println("En la ruta hay: [" + iContador + "] .Zip " + "Fin" );
		// devolver una lista de archivo List<File>
		return ListPath;
	}
}
