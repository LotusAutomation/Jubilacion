package co.com.bancolombia.certification.compararjsonvsxml.stepdefinitions;

import co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararJson;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.Actor;

public class CompararJsonVsXmlDefinition {

	private Actor actor = Actor.named("Lotus");

	@Given("^that I consult a Json file on a specific route from (.*), (.*), (.*), (.*), (.*), (.*).$")
	public void thatIConsultAJsonFileOnASpecificRouteFrom(String rutaFileXml, String rutaFileAnexo, String rutaFileJson,
			String rutaFileLog, String rutaFilePropiedades, String rutaFileXmlHijo) {
		actor.attemptsTo(CompararJson.conArchivoXml(rutaFileXml, rutaFileAnexo, rutaFileJson, rutaFileLog,
				rutaFilePropiedades, rutaFileXmlHijo));

		// actor.attemptsTo(CompararSoloDataJson.conArchivoXml());
		// actor.attemptsTo(CompararAttachmentsDeJson.conArchivoXml(rutaFileAnexo,rutaFilePropiedades));
	}

	@When("^I compare the information with the Xml file$")
	public void iCompareTheInformationWithTheXmlFile() {
	}

	@Then("^I should see that there is not message's errors in the log file$")
	public void iSeeSuccessfulValidationMessagesRegisteredSuccessfully() {
	}

}
