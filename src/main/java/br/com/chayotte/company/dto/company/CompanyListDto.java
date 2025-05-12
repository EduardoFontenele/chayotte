package br.com.chayotte.company.dto.company;

import br.com.chayotte.company.entity.BusinessSegment;
import br.com.chayotte.company.entity.CompanyType;

public record CompanyListDto(
        String name,
        String documentNumber,
        CompanyType type,
        BusinessSegment segment,
        boolean active
) {}
