package br.com.chayotte.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    // Validation patterns
    public static final String CNPJ_PATTERN = "^\\d{14}$";
    public static final String ZIPCODE_PATTERN = "^\\d{8}$";
    public static final String PHONE_PATTERN = "^\\+?[0-9]{10,14}$";

    // Size limits
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_TRADE_NAME_LENGTH = 100;
    public static final int MAX_REGISTRATION_LENGTH = 30;
    public static final int MAX_STREET_LENGTH = 100;
    public static final int MAX_NUMBER_LENGTH = 20;
    public static final int MAX_COMPLEMENT_LENGTH = 50;
    public static final int MAX_NEIGHBORHOOD_LENGTH = 50;
    public static final int MAX_CITY_LENGTH = 50;
    public static final int STATE_LENGTH = 2;
    public static final int MAX_COUNTRY_LENGTH = 50;
    public static final int MAX_CONTACT_NAME_LENGTH = 100;
}
