package br.com.chayotte.company.dto;

import br.com.chayotte.common.constants.Constants;
import br.com.chayotte.common.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDto(
        @NotBlank(message = ErrorMessages.STREET_REQUIRED)
        @Size(max = Constants.MAX_STREET_LENGTH, message = ErrorMessages.STREET_SIZE)
        String street,

        @NotBlank(message = ErrorMessages.NUMBER_REQUIRED)
        @Size(max = Constants.MAX_NUMBER_LENGTH, message = ErrorMessages.NUMBER_SIZE)
        String number,

        @Size(max = Constants.MAX_COMPLEMENT_LENGTH, message = ErrorMessages.COMPLEMENT_SIZE)
        String complement,

        @NotBlank(message = ErrorMessages.NEIGHBORHOOD_REQUIRED)
        @Size(max = Constants.MAX_NEIGHBORHOOD_LENGTH, message = ErrorMessages.NEIGHBORHOOD_SIZE)
        String neighborhood,

        @NotBlank(message = ErrorMessages.CITY_REQUIRED)
        @Size(max = Constants.MAX_CITY_LENGTH, message = ErrorMessages.CITY_SIZE)
        String city,

        @NotBlank(message = ErrorMessages.STATE_REQUIRED)
        @Size(min = Constants.STATE_LENGTH, max = Constants.STATE_LENGTH, message = ErrorMessages.STATE_SIZE)
        String state,

        @NotBlank(message = ErrorMessages.COUNTRY_REQUIRED)
        @Size(max = Constants.MAX_COUNTRY_LENGTH, message = ErrorMessages.COUNTRY_SIZE)
        String country,

        @NotBlank(message = ErrorMessages.ZIPCODE_REQUIRED)
        @Pattern(regexp = Constants.ZIPCODE_PATTERN, message = ErrorMessages.ZIPCODE_PATTERN)
        String zipCode
) {}

