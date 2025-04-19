package br.com.chayotte.bdd.config;

import br.com.chayotte.ChayotteApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ChayotteApplication.class)
public class CucumberSpringConfiguration {

    @LocalServerPort
    protected int port;

    public String getBaseUrl() {
        return "http:/localhost:" + port;
    }
}
