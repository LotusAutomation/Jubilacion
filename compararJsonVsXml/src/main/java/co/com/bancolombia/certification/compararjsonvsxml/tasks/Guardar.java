package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import static net.serenitybdd.core.Serenity.setSessionVariable;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class Guardar implements Task {

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

	public Guardar(String sMensajeTotalUniversalId, int iContTotalUniversalId, String sLineaSeparadora,
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
		setSessionVariable("sMensajeTotalUniversalId").to(sMensajeTotalUniversalId);
		setSessionVariable("iContTotalUniversalId").to(iContTotalUniversalId);
		setSessionVariable("sLineaSeparadora").to(sLineaSeparadora);
		setSessionVariable("sDatosUniversalIdFallidos").to(sDatosUniversalIdFallidos);
		setSessionVariable("bTieneHijos").to(bTieneHijos);
		setSessionVariable("sCantidadFallidos").to(sCantidadFallidos);
		setSessionVariable("iContUniversalIdExitoso").to(iContUniversalIdExitoso);
		setSessionVariable("sMensajeUiversalIdExitosos").to(sMensajeUiversalIdExitosos);
		setSessionVariable("iXmlVacios").to(iXmlVacios);
		setSessionVariable("sUniversalDamage").to(sUniversalDamage);
		setSessionVariable("rutaFileLogs").to(rutaFileLogs);

	}

	public static Guardar losValores(String sMensajeTotalUniversalId, int iContTotalUniversalId,
			String sLineaSeparadora, String sDatosUniversalIdFallidos, Boolean bTieneHijos, String sCantidadFallidos,
			int iContUniversalIdExitoso, String sMensajeUiversalIdExitosos, int iXmlVacios, String sUniversalDamage,
			String rutaFileLogs) {
		return Tasks.instrumented(Guardar.class, sMensajeTotalUniversalId, iContTotalUniversalId, sLineaSeparadora,
				sDatosUniversalIdFallidos, bTieneHijos, sCantidadFallidos, iContUniversalIdExitoso,
				sMensajeUiversalIdExitosos, iXmlVacios, sUniversalDamage, rutaFileLogs);
	}

}
