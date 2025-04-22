package br.com.chayotte.company.dto.company;

import br.com.chayotte.common.constants.Constants;
import br.com.chayotte.common.constants.ErrorMessages;
import br.com.chayotte.company.dto.AddressDto;
import br.com.chayotte.company.dto.ContactInfoDto;
import br.com.chayotte.company.model.BusinessSegment;
import br.com.chayotte.company.model.CompanyType;
import br.com.chayotte.company.model.TaxRegime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CompanyUpdateDto(
        @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH,
                message = ErrorMessages.COMPANY_NAME_SIZE)
        String name,

        @Size(max = Constants.MAX_TRADE_NAME_LENGTH, message = ErrorMessages.TRADE_NAME_SIZE)
        String tradeName,

        @Size(max = Constants.MAX_REGISTRATION_LENGTH, message = ErrorMessages.STATE_REGISTRATION_SIZE)
        String stateRegistration,

        @Size(max = Constants.MAX_REGISTRATION_LENGTH, message = ErrorMessages.MUNICIPAL_REGISTRATION_SIZE)
        String municipalRegistration,

        CompanyType type,

        BusinessSegment segment,

        @Valid
        AddressDto address,

        @Valid
        ContactInfoDto contactInfo,

        @Email(message = ErrorMessages.INVALID_FISCAL_EMAIL)
        String fiscalEmail,

        TaxRegime taxRegime
) {}

