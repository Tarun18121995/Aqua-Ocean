package com.innovative.coder.aqua.Facade;

import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.CompanyDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CompanyFacade {
    ResponseEntity<BaseResponseDto> createCompany(CompanyDto companyDto, HttpServletRequest request);

}
