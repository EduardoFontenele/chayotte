package br.com.chayotte.company.mapper;

import br.com.chayotte.company.dto.company.CompanyCreateDto;
import br.com.chayotte.company.model.Address;
import br.com.chayotte.company.model.Company;
import br.com.chayotte.company.model.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyCreateDto dto) {
        if (dto == null) {
            return null;
        }

        var company = new Company();
        company.setName(dto.name());
        company.setTradeName(dto.tradeName());
        company.setDocumentNumber(dto.documentNumber());
        company.setStateRegistration(dto.stateRegistration());
        company.setMunicipalRegistration(dto.municipalRegistration());
        company.setType(dto.type());
        company.setSegment(dto.segment());
        company.setTaxRegime(dto.taxRegime());
        company.setFiscalEmail(dto.fiscalEmail());
        company.setIsActive(true);

        if (dto.address() != null) {
            var address = new Address();
            address.setStreet(dto.address().street());
            address.setNumber(dto.address().number());
            address.setComplement(dto.address().complement());
            address.setNeighborhood(dto.address().neighborhood());
            address.setCity(dto.address().city());
            address.setState(dto.address().state());
            address.setCountry(dto.address().country());
            address.setZipCode(dto.address().zipCode());
            company.setAddress(address);
        }

        if (dto.contactInfo() != null) {
            var contactInfo = new ContactInfo();
            contactInfo.setPhone(dto.contactInfo().phone());
            contactInfo.setEmail(dto.contactInfo().email());
            contactInfo.setWebsite(dto.contactInfo().website());
            contactInfo.setContactName(dto.contactInfo().contactName());
            company.setContactInfo(contactInfo);
        }

        return company;
    }
}
