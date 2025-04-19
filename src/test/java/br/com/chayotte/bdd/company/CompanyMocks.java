package br.com.chayotte.bdd.company;

import br.com.chayotte.company.dto.AddressDto;
import br.com.chayotte.company.dto.ContactInfoDto;
import br.com.chayotte.company.dto.company.CompanyCreateDto;
import br.com.chayotte.company.model.BusinessSegment;
import br.com.chayotte.company.model.CompanyType;
import br.com.chayotte.company.model.TaxRegime;

public final class CompanyMocks {
    private CompanyMocks() {}

    public static CompanyCreateDto validCompanyCreateDto() {
        return new CompanyCreateDto(
                "Empresa Teste Ltda",
                "Empresa Teste",
                generateUniqueCnpj(),
                "123456789",
                "98765432",
                CompanyType.RETAIL,
                BusinessSegment.ELECTRONICS,
                validAddressDto(),
                validContactInfoDto(),
                "fiscal@empresateste.com.br",
                TaxRegime.SIMPLES_NACIONAL
        );
    }

    public static CompanyCreateDto companyCreateDtoWithMissingFields() {
        return new CompanyCreateDto(
                "Empresa Teste Ltda",
                null,
                generateUniqueCnpj(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static CompanyCreateDto companyCreateDtoWithInvalidDocumentNumber() {
        return new CompanyCreateDto(
                "Empresa Teste Ltda",
                "Empresa Teste",
                "123456",
                "123456789",
                "98765432",
                CompanyType.RETAIL,
                BusinessSegment.ELECTRONICS,
                validAddressDto(),
                validContactInfoDto(),
                "fiscal@empresateste.com.br",
                TaxRegime.SIMPLES_NACIONAL
        );
    }

    public static AddressDto validAddressDto() {
        return new AddressDto(
                "Rua das Flores",
                "123",
                "Sala 101",
                "Centro",
                "São Paulo",
                "SP",
                "Brasil",
                "01234567"
        );
    }

    public static ContactInfoDto validContactInfoDto() {
        return new ContactInfoDto(
                "11987654321",
                "contato@empresateste.com.br",
                "www.empresateste.com.br",
                "João Silva"
        );
    }

    public static String generateUniqueCnpj() {
        return String.format("%08d%06d",
                (int)(Math.random() * 100000000),
                System.currentTimeMillis() % 1000000);
    }
}
