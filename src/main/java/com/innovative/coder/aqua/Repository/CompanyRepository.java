package com.innovative.coder.aqua.Repository;

import com.innovative.coder.aqua.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    @Query(value = "SELECT * FROM company where is_deleted = false and email =:companyEmail", nativeQuery = true)
    Company findByCompanyEmail(String companyEmail);

}
