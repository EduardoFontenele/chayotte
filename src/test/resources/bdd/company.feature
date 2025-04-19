Feature: Company Registration and Management

  Background:
    Given the user is authenticated with appropriate permissions

  Scenario: Successfully register a new company
    When the user submits a request to register a valid company
    Then the system should register the company successfully
    And return a 201 Created status
    And provide a location header with the new company ID

  Scenario: Attempt to register a company with missing required fields
    When the user submits a request to register a company with missing fields
    Then the system should reject the registration
    And return a 400 Bad Request status
    And return validation errors for the missing required fields

  Scenario: Attempt to register a company with invalid document number
    When the user submits a request to register a company with invalid document number
    Then the system should reject the registration
    And return a 400 Bad Request status
    And return a validation error for the document number format

  Scenario: Retrieve a company by ID
    Given a company has been registered
    When the user requests the company details by its ID
    Then the system should return the company details
    And return a 200 OK status

  Scenario: Attempt to retrieve a non-existent company
    When the user requests a company with non-existent ID
    Then the system should return a 404 Not Found status