package br.com.chayotte.bdd.company;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/bdd",
        glue = {"br.com.chayotte.bdd.company", "br.com.chayotte.bdd.config"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CompanyTest {
}
