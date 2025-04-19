package br.com.chayotte.company.dto.company;

import br.com.chayotte.company.model.BusinessSegment;
import br.com.chayotte.company.model.CompanyType;

public record CompanyResponseDto(
        Long id,
        String name,
        String tradeName,
        String documentNumber,
        String stateRegistration,
        String municipalRegistration,
        CompanyType type,
        BusinessSegment segment
) {}
