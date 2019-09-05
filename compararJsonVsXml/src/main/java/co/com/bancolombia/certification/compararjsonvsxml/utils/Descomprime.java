package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Descomprime {

	public static int iCantidadZipErrados = 0;
	public static String sZipErrados = "";

	public static void archivoZip(String ficheroZip, String directorioSalida) throws Exception {

		final int TAM_BUFFER = 4096;
		byte[] buffer = new byte[TAM_BUFFER];

		ZipInputStream flujo = null;
		try {
			File nuevoDirectorio = new File(directorioSalida);
			nuevoDirectorio.mkdir();
			flujo = new ZipInputStream(new BufferedInputStream(new FileInputStream(ficheroZip)));
			ZipEntry entrada;

			while ((entrada = flujo.getNextEntry()) != null) {

				String nombreSalida = directorioSalida + File.separator + entrada.getName();
				if (entrada.isDirectory()) {

					File directorio = new File(nombreSalida);
					directorio.mkdirs();
				} else {
					BufferedOutputStream salida = null;
					try {
						int leido = 0;
						salida = new BufferedOutputStream(new FileOutputStream(nombreSalida), TAM_BUFFER);
						while ((leido = flujo.read(buffer, 0, TAM_BUFFER)) != -1) {

							salida.write(buffer, 0, leido);
						}
					} finally {
						if (salida != null) {
							salida.close();
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(".::Descomprime archivoZip::. EL sgte ZIP está vacío o no existe: " + ficheroZip);
			iCantidadZipErrados++;
			sZipErrados += ficheroZip + "\n";
		}
	}

	public static List<String> listFilesArchivoZip(String ficheroZip, String baseDeDatos) throws Exception {
		List<String> listFiles = new ArrayList<String>();
		ZipInputStream flujo = null;

		try {
			flujo = new ZipInputStream(new BufferedInputStream(new FileInputStream(ficheroZip)));
			ZipEntry entrada;
			while ((entrada = flujo.getNextEntry()) != null) {
				String nombreSalida = entrada.toString();
				listFiles.add(nombreSalida);
			}

		} catch (Exception e) {
			// Si entra en esta excepción, es porque EL sgte ZIP está vacío o no existe
			System.out
					.println(".::Descomprime listFilesArchivoZip::. EL sgte ZIP está vacío o no existe: " + ficheroZip);
			iCantidadZipErrados++;
			sZipErrados += ficheroZip + "\n";

			if (baseDeDatos.equals("COMEX")) {
				ValidarVacio.setEstadoNull("vacio pero lo necesito para COMEX");
			} else {

				// Se envía un estado "vacío" que se utiliza en este caso para indicar que no se
				// debe comparar con el Json porque el ZIP está vacío
				ValidarVacio.setEstadoNull("vacio");
			}

		}
		return listFiles;

	}

	public static void archivoGZip(String ficheroGZip, String directorioSalida) throws Exception {
		final int TAM_BUFFER = 4096;
		byte[] buffer = new byte[TAM_BUFFER];

		GZIPInputStream flujo = null;
		try {
			File nuevoDirectorio = new File(directorioSalida);
			nuevoDirectorio.mkdir();
			flujo = new GZIPInputStream(new BufferedInputStream(new FileInputStream(ficheroGZip)));
			ZipEntry entrada = null;

			String nombreSalida = directorioSalida + File.separator + entrada.getName();
			if (entrada.isDirectory()) {

				File directorio = new File(nombreSalida);
				directorio.mkdirs();
			} else {
				BufferedOutputStream salida = null;
				try {
					int leido = 0;
					salida = new BufferedOutputStream(new FileOutputStream(nombreSalida), TAM_BUFFER);
					while ((leido = flujo.read(buffer, 0, TAM_BUFFER)) != -1) {
						salida.write(buffer, 0, leido);
					}
				} finally {
					if (salida != null) {
						salida.close();
					}
				}
			}

		} catch (Exception e) {
			System.out.println(".::Descomprime .GZ::. EL sgte GZ está vacío o no existe: " + ficheroGZip);

		}

	}
}
