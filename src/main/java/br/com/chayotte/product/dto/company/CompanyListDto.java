package br.com.chayotte.product.dto.company;

import br.com.chayotte.company.model.BusinessSegment;
import br.com.chayotte.company.model.CompanyType;

public record CompanyListDto(
        String name,
        String documentNumber,
        CompanyType type,
        BusinessSegment segment,
        boolean active
) {}
