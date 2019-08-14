package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;

public class IniciarLaAplicacion implements Task {

	private String url;
	private WebDriver chrome;

	public IniciarLaAplicacion(WebDriver chrome) {
		this.chrome = chrome;
	}

	public <T extends Actor> void performAs(T actor) {
		actor.can(BrowseTheWeb.with(chrome));
		// el actor pudo hacer, para eventos q pasaron e instancia el driver
		actor.wasAbleTo(Open.url(url));
	}

	public static IniciarLaAplicacion conElNavegador(WebDriver chrome) {
		return Tasks.instrumented(IniciarLaAplicacion.class, chrome);
	}

	public IniciarLaAplicacion yEnLaUrl(String url) {
		this.url = url;
		return this;

	}
}
