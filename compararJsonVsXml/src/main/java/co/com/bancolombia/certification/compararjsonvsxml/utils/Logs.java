
package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logs {
	private static BufferedWriter bw;
	private static FileWriter fw;

	private static Logs instancia;

	private Logs() {
		try {
			fw = new FileWriter(SerenityProperties.sPATH_LOG_FILE, true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String rutaLogs(String nombreLog) {
		String ruta = "D:/Datos_Prueba_SubReclamos/" + nombreLog + ".log";
		
		return ruta;
	}

	public static void writeFile(String contenido) {
		String informacion = contenido;

		String ruta = SerenityProperties.sPATH_LOG_FILE;
		// String ruta = rutaLogs(nombreLog);
		// contenido = "Contenido de ejemplo";
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

	public static void createNewFile() {
		try {
			PrintWriter writer = new PrintWriter(SerenityProperties.sPATH_LOG_FILE, "UTF-8");
			writer.println("Primera línea");
			writer.println("Segunda línea");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void escribirLog(String texto) {
		try {
			if (texto == null)
				bw.write("null");
			else
				bw.write(texto);
			bw.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
