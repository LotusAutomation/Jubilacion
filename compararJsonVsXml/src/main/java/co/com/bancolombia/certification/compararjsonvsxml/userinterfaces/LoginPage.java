package co.com.bancolombia.certification.compararjsonvsxml.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {

	public static final Target TXT_USER = Target.the("txt user").locatedBy("//input[@name='username']");
	public static final Target TXT_PASSWD = Target.the("txt passwd").locatedBy("//input[@name='password']");
	public static final Target BTN_LOGIN = Target.the("Botón Logín").locatedBy("//input[@value='Log In']");
}
