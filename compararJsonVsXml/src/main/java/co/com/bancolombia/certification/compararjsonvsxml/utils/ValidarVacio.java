package co.com.bancolombia.certification.compararjsonvsxml.utils;

public class ValidarVacio {

	private static String estado = "";

	public ValidarVacio(String estadoNull) {
		this.estado = estadoNull;
	}

	public static String getEstadoNull() {
		return estado;
	}
	/*
	 * public void setEstadoNull(Boolean estadoNull) { this.estadoNull = estadoNull;
	 * }
	 */

	public static void setEstadoNull(String estadoNull) {
		estado = estadoNull;
	}

}
