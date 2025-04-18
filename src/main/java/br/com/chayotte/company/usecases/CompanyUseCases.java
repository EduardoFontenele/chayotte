package br.com.chayotte.company.usecases;

import br.com.chayotte.company.dto.company.CompanyCreateDto;
import br.com.chayotte.company.dto.company.CompanyResponseDto;
import br.com.chayotte.company.mapper.CompanyMapper;
import br.com.chayotte.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CompanyUseCases {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Long registerNewCompany(CompanyCreateDto dto) {
        var company = companyRepository.save(companyMapper.toEntity(dto));

        if(isNull(company.getId())) {
            throw new RuntimeException("Oh shit");
        }

        return company.getId();
    }

    public CompanyResponseDto findCompanyById(Long id) {
        return null;
    }
}
