
package co.com.bancolombia.certification.compararjsonvsxml.utils;

import static co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararJson.rutaFileLogs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logs {

	private static BufferedWriter bw;
	private static FileWriter fw;
	private static Logs instancia;

	private Logs() {
		try {
			fw = new FileWriter(rutaFileLogs, true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeFile(String contenido) {

		String informacion = contenido;
		String ruta = ReplaceCharacters.of(rutaFileLogs);

		File file = new File(ruta);
		// Si el archivo no existe es creado
		try {
			if (!file.exists()) {

				file.createNewFile();
			}
			fw = new FileWriter(file, true);

			bw = new BufferedWriter(fw);

			if (informacion == null) {
				bw.write("null");
			}

			else {
				bw.write(informacion);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
