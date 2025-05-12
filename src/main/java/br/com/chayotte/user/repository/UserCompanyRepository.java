package br.com.chayotte.user.repository;

import br.com.chayotte.user.entity.UserCompany;
import org.springframework.data.repository.CrudRepository;

public interface UserCompanyRepository extends CrudRepository<UserCompany, Long> {
}
