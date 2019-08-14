package co.com.bancolombia.certification.compararjsonvsxml.stepdefinitions;

import co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararJson;
import co.com.bancolombia.certification.compararjsonvsxml.tasks.CompararSoloDataJson;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.Actor;

public class CompararJsonVsXmlDefinition {

	private Actor actor = Actor.named("Diego");

//	private String urlLotusServer = SerenityProperties.sURL_SERVER;
//	private List<String> urlsDeLostus = ListaUrlLotusServer.porCadaUniversalId();;
//	private String sUserName = SerenityProperties.sUSER_NAME;
//	private String sPassword = SerenityProperties.sPASSWORD; 

	@Given("^that I consult a Json file on a specific route$")
	public void thatIConsultAJsonFileOnASpecificRoute() {
	}

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

	@Given("^that I consume an XML file in the lotus site$")
	public void thatIConsumeAnXMLFileInTheLotusSite() {
		// ListaUrlLotusServer.porCadaUniversalId();
		/*
		 * ListaUrlLotusServer.mostrarListaUrls(); for (String urlLotusServer :
		 * urlsDeLostus) {
		 * actor.wasAbleTo(IniciarLaAplicacion.conElNavegador(chrome).yEnLaUrl(
		 * urlLotusServer));
		 * actor.wasAbleTo(Loguearse.conElUsuario(sUserName).yLaContrasenia(sPassword));
		 * }
		 */

	}

	@When("^I compare the information with the migrated Json file$")
	public void iCompareTheInformationWithTheMigratedJsonFile() {

	}

	@Then("^I see successful validation messages Registered Successfully$")
	public void iSeeSuccessfulValidationMessagesRegisteredSuccessfully() {

	}

}
