package br.com.chayotte.company.mapper;

import br.com.chayotte.company.dto.AddressDto;
import br.com.chayotte.company.dto.ContactInfoDto;
import br.com.chayotte.company.dto.company.CompanyCreateDto;
import br.com.chayotte.company.dto.company.CompanyResponseDto;
import br.com.chayotte.company.dto.company.CompanyUpdateDto;
import br.com.chayotte.company.entity.Address;
import br.com.chayotte.company.entity.Company;
import br.com.chayotte.company.entity.ContactInfo;
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

    public CompanyResponseDto toResponseDto(Company entity) {
        if (entity == null) {
            return null;
        }

        return new CompanyResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getTradeName(),
                entity.getDocumentNumber(),
                entity.getStateRegistration(),
                entity.getMunicipalRegistration(),
                entity.getType(),
                entity.getSegment()
        );
    }

    private AddressDto toAddressDto(Address entity) {
        if (entity == null) return null;

        return new AddressDto(
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getZipCode()
        );
    }

    private ContactInfoDto toContactInfoDto(ContactInfo entity) {
        if (entity == null) return null;

        return new ContactInfoDto(
                entity.getPhone(),
                entity.getEmail(),
                entity.getWebsite(),
                entity.getContactName()
        );
    }

    public Company updateEntityFromDto(Company existingCompany, CompanyUpdateDto dto) {
        if (dto.name() != null) {
            existingCompany.setName(dto.name());
        }

        if (dto.tradeName() != null) {
            existingCompany.setTradeName(dto.tradeName());
        }

        if (dto.stateRegistration() != null) {
            existingCompany.setStateRegistration(dto.stateRegistration());
        }

        if (dto.municipalRegistration() != null) {
            existingCompany.setMunicipalRegistration(dto.municipalRegistration());
        }

        if (dto.type() != null) {
            existingCompany.setType(dto.type());
        }

        if (dto.segment() != null) {
            existingCompany.setSegment(dto.segment());
        }

        if (dto.address() != null) {
            updateAddress(existingCompany.getAddress(), dto.address());
        }

        if (dto.contactInfo() != null) {
            updateContactInfo(existingCompany.getContactInfo(), dto.contactInfo());
        }

        if (dto.fiscalEmail() != null) {
            existingCompany.setFiscalEmail(dto.fiscalEmail());
        }

        if (dto.taxRegime() != null) {
            existingCompany.setTaxRegime(dto.taxRegime());
        }

        return existingCompany;
    }

    private void updateAddress(Address existingAddress, AddressDto addressDto) {
        if (addressDto.street() != null) {
            existingAddress.setStreet(addressDto.street());
        }

        if (addressDto.number() != null) {
            existingAddress.setNumber(addressDto.number());
        }

        if (addressDto.complement() != null) {
            existingAddress.setComplement(addressDto.complement());
        }

        if (addressDto.neighborhood() != null) {
            existingAddress.setNeighborhood(addressDto.neighborhood());
        }

        if (addressDto.city() != null) {
            existingAddress.setCity(addressDto.city());
        }

        if (addressDto.state() != null) {
            existingAddress.setState(addressDto.state());
        }

        if (addressDto.country() != null) {
            existingAddress.setCountry(addressDto.country());
        }

        if (addressDto.zipCode() != null) {
            existingAddress.setZipCode(addressDto.zipCode());
        }
    }

    private void updateContactInfo(ContactInfo existingContactInfo, ContactInfoDto contactInfoDto) {
        if (contactInfoDto.phone() != null) {
            existingContactInfo.setPhone(contactInfoDto.phone());
        }

        if (contactInfoDto.email() != null) {
            existingContactInfo.setEmail(contactInfoDto.email());
        }

        if (contactInfoDto.website() != null) {
            existingContactInfo.setWebsite(contactInfoDto.website());
        }

        if (contactInfoDto.contactName() != null) {
            existingContactInfo.setContactName(contactInfoDto.contactName());
        }
    }
}