package co.com.bancolombia.certification.compararjsonvsxml.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class BuscarJson implements Task {

	@Override
	public <T extends Actor> void performAs(T actor) {
		actor.attemptsTo();
	}

}
