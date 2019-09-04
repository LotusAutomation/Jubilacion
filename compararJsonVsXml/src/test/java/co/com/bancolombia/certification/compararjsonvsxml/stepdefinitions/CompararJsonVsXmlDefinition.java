package co.com.bancolombia.certification.compararjsonvsxml.stepdefinitions;

import static net.serenitybdd.core.Serenity.sessionVariableCalled;

import co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararJson;
import co.com.bancolombia.certification.compararjsonvsxml.tasks.Construir;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.serenitybdd.screenplay.Actor;

public class CompararJsonVsXmlDefinition {

	private Actor actor = Actor.named("Lotus");

	@Given("^that I compare a Json file on a specific route from (.*), (.*), (.*), (.*), (.*), (.*) with the Xml file$")
	public void thatIConsultAJsonFileOnASpecificRouteFrom(String rutaFileXml, String rutaFileAnexo, String rutaFileJson,
			String rutaFileLog, String rutaFilePropiedades, String rutaFileXmlHijo) {
		actor.attemptsTo(CompararJson.conArchivoXml(rutaFileXml, rutaFileAnexo, rutaFileJson, rutaFileLog,
				rutaFilePropiedades, rutaFileXmlHijo, "COMEX"));

//		actor.attemptsTo(CompararSoloDataJson.conArchivoXml(rutaFileXml, rutaFileAnexo, rutaFileJson, rutaFileLog,
//				rutaFilePropiedades, rutaFileXmlHijo));
//		actor.attemptsTo(CompararAttachmentsDeJson.conArchivoXml(rutaFileAnexo, rutaFilePropiedades, rutaFileJson,
//				rutaFileLog, "COMEX"));
	}

	@Then("^I should see that there is not message's errors in the log file$")
	public void iSeeSuccessfulValidationMessagesRegisteredSuccessfully() {

		actor.attemptsTo(Construir.elReporte(sessionVariableCalled("sMensajeTotalUniversalId"),
				sessionVariableCalled("iContTotalUniversalId"), sessionVariableCalled("sLineaSeparadora"),
				sessionVariableCalled("sDatosUniversalIdFallidos"), sessionVariableCalled("bTieneHijos"),
				sessionVariableCalled("sCantidadFallidos"), sessionVariableCalled("iContUniversalIdExitoso"),
				sessionVariableCalled("sMensajeUiversalIdExitosos"), sessionVariableCalled("iXmlVacios"),
				sessionVariableCalled("sUniversalDamage"), sessionVariableCalled("rutaFileLogs")));
	}

}
