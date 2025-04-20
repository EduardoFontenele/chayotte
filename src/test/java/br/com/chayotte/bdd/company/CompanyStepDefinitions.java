package br.com.chayotte.bdd.company;

import br.com.chayotte.bdd.config.BaseStepDefinitions;
import br.com.chayotte.common.constants.ErrorMessages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CompanyStepDefinitions extends BaseStepDefinitions {

    private Long savedCompanyId;

    @Given("the user is authenticated with appropriate permissions")
    public void userIsAuthenticatedWithAppropriatePermissions() {
        setupJsonHeaders();
        addAuthToken("jwt-token-for-admin-user");
    }

    @Given("a company has been registered")
    public void companyHasBeenRegistered() {
        clearAll();
        setupJsonHeaders();
        addAuthToken("jwt-token-for-admin-user");

        setRequestBody(CompanyMocks.validCompanyCreateDto());

        var response = executePost("/api/v1/companies");
        var responseBody = response.getBody().asString();

        assertEquals(201, response.getStatusCode(), "Falha ao registrar empresa. Resposta: " + responseBody);

        var locationHeader = response.getHeader("Location");
        assertNotNull(locationHeader, "Header Location não encontrado na resposta");

        var parts = locationHeader.split("/");
        savedCompanyId = Long.parseLong(parts[parts.length - 1]);
    }

    @When("the user submits a request to register a valid company")
    public void userSubmitsRequestToRegisterValidCompany() {
        setRequestBody(CompanyMocks.validCompanyCreateDto());
        executePost("api/v1/companies");
    }

    @When("the user submits a request to register a company with missing fields")
    public void userSubmitsRequestToRegisterCompanyWithMissingFields() {
        setRequestBody(CompanyMocks.companyCreateDtoWithMissingFields());
        executePost("api/v1/companies");
    }

    @When("the user submits a request to register a company with invalid document number")
    public void userSubmitsRequestToRegisterCompanyWithInvalidDocumentNumber() {
        setRequestBody(CompanyMocks.companyCreateDtoWithInvalidDocumentNumber());
        executePost("api/v1/companies");
    }

    @When("the user requests the company details by its ID")
    public void userRequestsCompanyDetailsById() {
        executeGet("api/v1/companies/" + savedCompanyId);
    }

    @When("the user requests a company with non-existent ID")
    public void userRequestsCompanyWithNonExistentId() {
        executeGet("api/v1/companies/12345678");
    }

    @Then("the system should register the company successfully")
    public void systemShouldRegisterCompanySuccessfully() {
    }

    @Then("return a {int} Created status")
    public void returnCreatedStatus(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("provide a location header with the new company ID")
    public void provideLocationHeaderWithNewCompanyId() {
        var locationHeader = response.getHeader("Location");
        assertNotNull(locationHeader);

        var parts = locationHeader.split("/");
        savedCompanyId = Long.parseLong(parts[parts.length - 1]);
    }

    @Then("the system should reject the registration")
    public void systemShouldRejectRegistration() {
    }

    @Then("return a {int} Bad Request status")
    public void returnBadRequestStatus(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("return validation errors for the missing required fields")
    public void returnValidationErrorsForMissingRequiredFields() {
        var jsonPath = response.jsonPath();
        assertNotNull(jsonPath.getList("constraintViolations"));
        assert(!jsonPath.getList("constraintViolations").isEmpty());
    }

    @Then("return a validation error for the document number format")
    public void returnValidationErrorForDocumentNumberFormat() {
        var responseBody = response.getBody().asString();
        assert(responseBody.contains(ErrorMessages.DOCUMENT_NUMBER_PATTERN));
    }

    @Then("the system should return the company details")
    public void systemShouldReturnCompanyDetails() {
        var jsonPath = response.jsonPath();
        jsonPath.getLong("id");
        assertNotNull(jsonPath.getString("name"));
        assertEquals("Empresa Teste Ltda", jsonPath.getString("name"));
    }

    @Then("return a {int} OK status")
    public void returnOkStatus(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the system should return a {int} Not Found status")
    public void systemShouldReturnNotFoundStatus(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
}