package com.innovative.coder.aqua.Service;

import com.innovative.coder.aqua.Dto.AquaAdminSignupDto;
import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.CompanyDto;
import com.innovative.coder.aqua.Model.Company;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CompanyService {

    Company saveCompany(CompanyDto companyDto, BaseResponseDto baseResponseDto);

}
