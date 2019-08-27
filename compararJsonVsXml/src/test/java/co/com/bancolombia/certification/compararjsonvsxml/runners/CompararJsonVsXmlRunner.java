package co.com.bancolombia.certification.compararjsonvsxml.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features="src/test/resources/features/compararjsonvsxmml.feature",
					glue= "co.com.bancolombia.certification.compararjsonvsxml.stepdefinitions",
					tags="@CompareData",
					snippets=SnippetType.CAMELCASE)
public class CompararJsonVsXmlRunner {

}