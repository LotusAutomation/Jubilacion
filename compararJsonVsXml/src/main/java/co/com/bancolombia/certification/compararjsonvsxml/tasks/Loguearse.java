package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import static co.com.bancolombia.certification.compararjsonvsxml.userinterfaces.LoginPage.BTN_LOGIN;
import static co.com.bancolombia.certification.compararjsonvsxml.userinterfaces.LoginPage.TXT_PASSWD;
import static co.com.bancolombia.certification.compararjsonvsxml.userinterfaces.LoginPage.TXT_USER;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class Loguearse implements Task {

	private String sUsuario;
	private String sPassword;

	public Loguearse(String usuario) {
		this.sUsuario = usuario;
	}

	@Override
	public <T extends Actor> void performAs(T actor) {
		actor.attemptsTo(Enter.theValue(sUsuario).into(TXT_USER));
		actor.attemptsTo(Enter.theValue(sPassword).into(TXT_PASSWD));
		actor.attemptsTo(Click.on(BTN_LOGIN));
	}

	public static Loguearse conElUsuario(String usuario) {
		return Tasks.instrumented(Loguearse.class, usuario);
	}

	public Loguearse yLaContrasenia(String password) {
		this.sPassword = password;
		return this;
	}

}
