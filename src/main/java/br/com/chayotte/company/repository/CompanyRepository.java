package br.com.chayotte.company.repository;

import br.com.chayotte.company.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
