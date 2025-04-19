package br.com.chayotte.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public static final String COMPANY_NAME_REQUIRED = "Company name is required";
    public static final String COMPANY_NAME_SIZE = "Company name must be between 2 and 100 characters";
    public static final String TRADE_NAME_SIZE = "Trade name must not exceed 100 characters";
    public static final String DOCUMENT_NUMBER_REQUIRED = "Document number is required";
    public static final String DOCUMENT_NUMBER_PATTERN = "Document number must be exactly 14 digits (CNPJ format)";
    public static final String STATE_REGISTRATION_SIZE = "State registration must not exceed 30 characters";
    public static final String MUNICIPAL_REGISTRATION_SIZE = "Municipal registration must not exceed 30 characters";
    public static final String COMPANY_TYPE_REQUIRED = "Company type is required";
    public static final String BUSINESS_SEGMENT_REQUIRED = "Business segment is required";
    public static final String ADDRESS_REQUIRED = "Address is required";
    public static final String CONTACT_INFO_REQUIRED = "Contact information is required";
    public static final String INVALID_FISCAL_EMAIL = "Invalid fiscal email format";

    // Address validation messages
    public static final String STREET_REQUIRED = "Street is required";
    public static final String STREET_SIZE = "Street must not exceed 100 characters";
    public static final String NUMBER_REQUIRED = "Number is required";
    public static final String NUMBER_SIZE = "Number must not exceed 20 characters";
    public static final String COMPLEMENT_SIZE = "Complement must not exceed 50 characters";
    public static final String NEIGHBORHOOD_REQUIRED = "Neighborhood is required";
    public static final String NEIGHBORHOOD_SIZE = "Neighborhood must not exceed 50 characters";
    public static final String CITY_REQUIRED = "City is required";
    public static final String CITY_SIZE = "City must not exceed 50 characters";
    public static final String STATE_REQUIRED = "State is required";
    public static final String STATE_SIZE = "State must be 2 characters";
    public static final String COUNTRY_REQUIRED = "Country is required";
    public static final String COUNTRY_SIZE = "Country must not exceed 50 characters";
    public static final String ZIPCODE_REQUIRED = "ZIP code is required";
    public static final String ZIPCODE_PATTERN = "ZIP code must be 8 digits";

    // Contact info validation messages
    public static final String PHONE_REQUIRED = "Phone is required";
    public static final String PHONE_PATTERN = "Phone must be 10-14 digits, with an optional + prefix";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String CONTACT_NAME_REQUIRED = "Contact name is required";
    public static final String CONTACT_NAME_SIZE = "Contact name must not exceed 100 characters";
}
