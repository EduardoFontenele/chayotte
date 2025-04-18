package br.com.chayotte.order.dto;

import br.com.chayotte.common.constants.Constants;
import br.com.chayotte.common.constants.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactInfoDto(
        @NotBlank(message = ErrorMessages.PHONE_REQUIRED)
        @Pattern(regexp = Constants.PHONE_PATTERN, message = ErrorMessages.PHONE_PATTERN)
        String phone,

        @NotBlank(message = ErrorMessages.EMAIL_REQUIRED)
        @Email(message = ErrorMessages.INVALID_EMAIL)
        String email,

        String website,

        @NotBlank(message = ErrorMessages.CONTACT_NAME_REQUIRED)
        @Size(max = Constants.MAX_CONTACT_NAME_LENGTH, message = ErrorMessages.CONTACT_NAME_SIZE)
        String contactName
) {}
