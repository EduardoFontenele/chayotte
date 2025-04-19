package br.com.chayotte.bdd.company;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/bdd",
        glue = {"br.com.chayotte.bdd.company", "br.com.chayotte.bdd.config"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompanyTest {
}
