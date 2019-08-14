package co.com.bancolombia.certification.compararjsonvsxml.exceptions;

public class ProblemaDeRed extends Exception {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	public static final String CONECTION_FAIL = "La página no puede cargar en este momento...";

	public ProblemaDeRed(String mensaje, Throwable cause) {

		super(mensaje, cause);

	}

}
