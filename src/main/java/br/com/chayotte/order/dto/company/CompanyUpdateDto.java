package br.com.chayotte.order.dto.company;

import br.com.chayotte.company.model.BusinessSegment;
import br.com.chayotte.company.model.CompanyType;
import br.com.chayotte.company.model.TaxRegime;
import br.com.chayotte.order.dto.AddressDto;
import br.com.chayotte.order.dto.ContactInfoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CompanyUpdateDto(
        @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
        String name,

        @Size(max = 100, message = "Trade name must not exceed 100 characters")
        String tradeName,

        @Size(max = 30, message = "State registration must not exceed 30 characters")
        String stateRegistration,

        @Size(max = 30, message = "Municipal registration must not exceed 30 characters")
        String municipalRegistration,

        CompanyType type,

        BusinessSegment segment,

        @Valid
        AddressDto address,

        @Valid
        ContactInfoDto contactInfo,

        Boolean active,

        @Email(message = "Invalid fiscal email format")
        String fiscalEmail,

        TaxRegime taxRegime
) {}

