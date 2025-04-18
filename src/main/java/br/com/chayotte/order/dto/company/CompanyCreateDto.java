package br.com.chayotte.order.dto.company;

import br.com.chayotte.common.constants.Constants;
import br.com.chayotte.common.constants.ErrorMessages;
import br.com.chayotte.company.model.BusinessSegment;
import br.com.chayotte.company.model.CompanyType;
import br.com.chayotte.company.model.TaxRegime;
import br.com.chayotte.order.dto.AddressDto;
import br.com.chayotte.order.dto.ContactInfoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyCreateDto(
        @NotBlank(message = ErrorMessages.COMPANY_NAME_REQUIRED)
        @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH,
                message = ErrorMessages.COMPANY_NAME_SIZE)
        String name,

        @Size(max = Constants.MAX_TRADE_NAME_LENGTH, message = ErrorMessages.TRADE_NAME_SIZE)
        String tradeName,

        @NotBlank(message = ErrorMessages.DOCUMENT_NUMBER_REQUIRED)
        @Pattern(regexp = Constants.CNPJ_PATTERN, message = ErrorMessages.DOCUMENT_NUMBER_PATTERN)
        String documentNumber,

        @Size(max = Constants.MAX_REGISTRATION_LENGTH, message = ErrorMessages.STATE_REGISTRATION_SIZE)
        String stateRegistration,

        @Size(max = Constants.MAX_REGISTRATION_LENGTH, message = ErrorMessages.MUNICIPAL_REGISTRATION_SIZE)
        String municipalRegistration,

        @NotNull(message = ErrorMessages.COMPANY_TYPE_REQUIRED)
        CompanyType type,

        @NotNull(message = ErrorMessages.BUSINESS_SEGMENT_REQUIRED)
        BusinessSegment segment,

        @Valid @NotNull(message = ErrorMessages.ADDRESS_REQUIRED)
        AddressDto address,

        @Valid @NotNull(message = ErrorMessages.CONTACT_INFO_REQUIRED)
        ContactInfoDto contactInfo,

        @Email(message = ErrorMessages.INVALID_FISCAL_EMAIL)
        String fiscalEmail,

        TaxRegime taxRegime
) {}
