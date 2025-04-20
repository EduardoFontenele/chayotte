package br.com.chayotte.company.usecases;

import br.com.chayotte.common.exception.ApiException;
import br.com.chayotte.company.dto.company.CompanyCreateDto;
import br.com.chayotte.company.dto.company.CompanyResponseDto;
import br.com.chayotte.company.mapper.CompanyMapper;
import br.com.chayotte.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static br.com.chayotte.common.utils.LogUtils.maskDocument;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyUseCases {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Long registerNewCompany(CompanyCreateDto dto) {
        var company = companyRepository.save(companyMapper.toEntity(dto));

        if(isNull(company.getId())) {
            log.info("Something went wrong while creating resource for company with CNPJ {}", maskDocument(dto.documentNumber()));
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while creating resource. Please try again");
        }

        return company.getId();
    }

    public CompanyResponseDto findCompanyById(Long id) {
        return companyRepository.findById(id)
                .map(companyMapper::toResponseDto)
                .orElseThrow(() -> {
                    log.info("Company not found with id: {}", id);
                    return new NoSuchElementException("Company not found with id: " + id);
                });
    }
}
