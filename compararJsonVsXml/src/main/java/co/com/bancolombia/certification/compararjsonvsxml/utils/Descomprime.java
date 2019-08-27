package co.com.bancolombia.certification.compararjsonvsxml.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

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
			sZipErrados += ficheroZip +"\n"; 
		}
	}

	public static List<String> listFilesArchivoZip(String ficheroZip) throws Exception {
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
			System.out.println(".::Descomprime listFilesArchivoZip::. EL sgte ZIP está vacío o no existe: " + ficheroZip);
			iCantidadZipErrados++;
			sZipErrados += ficheroZip +"\n"; 
		}
		return listFiles;
		
		/*
		 		FileInputStream fis = null;
		try {
			fis = new FileInputStream("C:/Users/QVISION/Documents/Reclamos_3BD/reclamos_10-/reclamos-/63DC2773D80FBC32052576B30066D326.zip");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
	    ZipEntry entry;
	    int i=0;
	    try {
			while((entry = zis.getNextEntry()) != null) {
			   i=i+1; 
			    System.out.println(entry);
			    System.out.println(i);
			    //read from zis until available
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		 */
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

		/*
		 * try { FileInputStream fis = new FileInputStream(gzipFile); GZIPInputStream
		 * gis = new GZIPInputStream(fis); FileOutputStream fos = new
		 * FileOutputStream(newFile); byte[] buffer = new byte[1024]; int len;
		 * while((len = gis.read(buffer)) != -1){ fos.write(buffer, 0, len); } //close
		 * resources fos.close(); gis.close(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
	}
}
