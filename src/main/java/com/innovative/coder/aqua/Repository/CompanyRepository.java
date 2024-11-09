package com.innovative.coder.aqua.Repository;

import com.innovative.coder.aqua.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
