package com.innovative.coder.aqua.ServiceImplimentation;

import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.CompanyDto;
import com.innovative.coder.aqua.Model.Company;
import com.innovative.coder.aqua.Repository.CompanyRepository;
import com.innovative.coder.aqua.Service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    public Company saveCompany(CompanyDto companyDto, BaseResponseDto baseResponseDto) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDto, company);
        return companyRepository.save(company);
    }
}
