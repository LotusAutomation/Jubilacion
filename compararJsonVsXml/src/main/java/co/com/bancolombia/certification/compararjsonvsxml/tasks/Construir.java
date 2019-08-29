package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import co.com.bancolombia.certification.compararjsonvsxml.utils.Descomprime;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ElementosJson;
import co.com.bancolombia.certification.compararjsonvsxml.utils.ListaDeFallidos;
import co.com.bancolombia.certification.compararjsonvsxml.utils.Logs;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class Construir implements Task {

	private String sMensajeTotalUniversalId;
	private int iContTotalUniversalId;
	private String sLineaSeparadora;
	private String sDatosUniversalIdFallidos;
	private Boolean bTieneHijos;
	private String sCantidadFallidos;
	private int iContUniversalIdExitoso;
	private String sMensajeUiversalIdExitosos;
	private int iXmlVacios;
	private String sUniversalDamage;
	private String rutaFileLogs;

	public Construir(String sMensajeTotalUniversalId, int iContTotalUniversalId, String sLineaSeparadora,
			String sDatosUniversalIdFallidos, Boolean bTieneHijos, String sCantidadFallidos,
			int iContUniversalIdExitoso, String sMensajeUiversalIdExitosos, int iXmlVacios, String sUniversalDamage,
			String rutaFileLogs) {
		this.sMensajeTotalUniversalId = sMensajeTotalUniversalId;
		this.iContTotalUniversalId = iContTotalUniversalId;
		this.sLineaSeparadora = sLineaSeparadora;
		this.sDatosUniversalIdFallidos = sDatosUniversalIdFallidos;
		this.bTieneHijos = bTieneHijos;
		this.sCantidadFallidos = sCantidadFallidos;
		this.iContUniversalIdExitoso = iContUniversalIdExitoso;
		this.sMensajeUiversalIdExitosos = sMensajeUiversalIdExitosos;
		this.iXmlVacios = iXmlVacios;
		this.sUniversalDamage = sUniversalDamage;
		this.rutaFileLogs = rutaFileLogs;
	}

	@Override
	public <T extends Actor> void performAs(T actor) {

		// -------------------Construcción de información---------------------------

		// ---Se llena el archivo ResumenPruebasXXX.log con los resultados de la prueba

		int fallidos = 0;

		sMensajeTotalUniversalId = "El total de Universal Id validados es de [ " + iContTotalUniversalId + " ]";
		Logs.writeFile(sLineaSeparadora + sMensajeTotalUniversalId + "\n" + sLineaSeparadora, rutaFileLogs);

		// Se reune la información de los UniversalID (PADRES) fallidos (si existen, es
		// decir, si
		// la lista de fallidos es mayor a cero)
		if (ListaDeFallidos.objetoCampoFallido.size() != 0) {

			fallidos = 1;
			String idInicial = ListaDeFallidos.objetoCampoFallido.get(0).getsUniversaID();

			sDatosUniversalIdFallidos = ("UniversalId fallido: [ " + idInicial + " ] " + "Campo: ["
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsNombreCampo() + "],  DatoXML: "
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsValorCampoXml()
					+ "|  ---ES DIFERENTE DE:---  DatoJson: |"
					+ ListaDeFallidos.objetoCampoFallido.get(0).getsValorCampoJson());

			Logs.writeFile(sDatosUniversalIdFallidos, rutaFileLogs);

			for (int x = 1; x < ListaDeFallidos.objetoCampoFallido.size(); x++) {

				if (idInicial.equals(ListaDeFallidos.objetoCampoFallido.get(x).getsUniversaID())) {

					sDatosUniversalIdFallidos = ("UniversalId fallido: [ " + idInicial + " ] " + "Campo: ["
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsNombreCampo() + "],  DatoXML: "
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoXml()
							+ "|  ---ES DIFERENTE DE:---  DatoJson: |"
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoJson());

					Logs.writeFile(sDatosUniversalIdFallidos, rutaFileLogs);

				} else {

					idInicial = ListaDeFallidos.objetoCampoFallido.get(x).getsUniversaID();
					// System.out.println(idInicial);
					sDatosUniversalIdFallidos = ("UniversalId fallido: [ " + idInicial + " ] " + "Campo: [" + " "
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsNombreCampo() + "],  DatoXML: "
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoXml()
							+ "|  ---ES DIFERENTE DE:---  DatoJson: |"
							+ ListaDeFallidos.objetoCampoFallido.get(x).getsValorCampoJson());

					fallidos++;

					Logs.writeFile(sDatosUniversalIdFallidos, rutaFileLogs);

				}

			}

		}

		// Se elimina la información que pueda contener la Lista de UniversalId
		// fallidos, con el fin de que la información no se repita cuando se hacen
		// varias ejecuciones en una misma prueba
		ListaDeFallidos.objetoCampoFallido.clear();

		// Se reune la información de los UniversalID (HIJOS) fallidos (si existen, es
		// decir, si
		// la lista de fallidos es mayor a cero)
		if (bTieneHijos) {
			// ------Escribir en el Log la cantidad de fallidos si tiene hijos------

			sCantidadFallidos = sLineaSeparadora + "\n" + "La cantidad de Universal Id Fallidos es de: [ "
					+ ElementosJson.iContador + " ]" + "\n" + sLineaSeparadora;
			System.out.println(sCantidadFallidos);
			Logs.writeFile(sCantidadFallidos, rutaFileLogs);

			iContUniversalIdExitoso = iContTotalUniversalId - ElementosJson.iContador;

		} else {

			// --------Escribir en el Log la cantidad de fallidos sin hijos------
			sCantidadFallidos = sLineaSeparadora + "\n" + "La cantidad de Universal Id Fallidos es de: [ " + fallidos
					+ " ]" + "\n" + sLineaSeparadora;

			System.out.println(sCantidadFallidos);
			Logs.writeFile(sCantidadFallidos, rutaFileLogs);

			iContUniversalIdExitoso = iContTotalUniversalId - fallidos;
		}

		sMensajeUiversalIdExitosos = sLineaSeparadora + "\n"
				+ " La cantidad de Universal Id exitosos validados es de: [ " + iContUniversalIdExitoso + " ] "
				+ sLineaSeparadora;
		Logs.writeFile(sMensajeUiversalIdExitosos, rutaFileLogs);

		// ---Información que se muestra por consola-----

		System.out.println("==============================================================");
		System.out.println("Cantidad de Universal Id validados es: " + iContTotalUniversalId);

		System.out.println("==============================================================");
		System.out.println(
				"Cantidad de Xml Vacíos o inexistentes: " + iXmlVacios + " \n Son: " + sUniversalDamage + " \n");
		System.out.println("==============================================================");

		System.out.println("==============================================================");
		System.out.println("Cantidad de .ZIP Vacíos o inexistentes: " + Descomprime.iCantidadZipErrados + " \n Son: \n"
				+ Descomprime.sZipErrados + " \n");
		System.out.println("==============================================================");
		System.out.println(sCantidadFallidos);

	}

	public static Construir elReporte(String sMensajeTotalUniversalId, int iContTotalUniversalId,
			String sLineaSeparadora, String sDatosUniversalIdFallidos, Boolean bTieneHijos, String sCantidadFallidos,
			int iContUniversalIdExitoso, String sMensajeUiversalIdExitosos, int iXmlVacios, String sUniversalDamage,
			String rutaFileLogs) {
		return Tasks.instrumented(Construir.class, sMensajeTotalUniversalId, iContTotalUniversalId, sLineaSeparadora,
				sDatosUniversalIdFallidos, bTieneHijos, sCantidadFallidos, iContUniversalIdExitoso,
				sMensajeUiversalIdExitosos, iXmlVacios, sUniversalDamage, rutaFileLogs);

	}

}
