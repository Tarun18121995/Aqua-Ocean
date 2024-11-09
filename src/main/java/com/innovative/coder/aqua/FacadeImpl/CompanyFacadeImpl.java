package com.innovative.coder.aqua.FacadeImpl;

import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.CompanyDto;
import com.innovative.coder.aqua.Facade.CompanyFacade;
import com.innovative.coder.aqua.Model.Company;
import com.innovative.coder.aqua.Repository.CompanyRepository;
import com.innovative.coder.aqua.Service.CompanyService;
import com.innovative.coder.aqua.ServiceImplimentation.AsyncServiceImpl;
import com.innovative.coder.aqua.applicationData.ApplicationConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CompanyFacadeImpl implements CompanyFacade {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AsyncServiceImpl asyncService;
    @Override
    public ResponseEntity<BaseResponseDto> createCompany(CompanyDto companyDto, HttpServletRequest request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        try {
            if(!ObjectUtils.isEmpty(companyDto)){
                if(!ObjectUtils.isEmpty(companyDto.getEmail())){
                  Company companyDetails = companyRepository.findByCompanyEmail(companyDto.getEmail());
                  if(ObjectUtils.isEmpty(companyDetails)){
                      Company savedCompany = companyService.saveCompany(companyDto, baseResponseDto);
                      baseResponseDto.setMessage(ApplicationConstants.COMPANY_CREATED);
                      asyncService.adminCreation(savedCompany, companyDto.getPassword());
                  }else {
                      baseResponseDto.setMessage(ApplicationConstants.COMPANY_EXISTED);
                      return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
                  }
                }else {
                    baseResponseDto.setMessage(ApplicationConstants.PROVIDE_EMAIL);
                    return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
            baseResponseDto.setMessage(exception.getLocalizedMessage());
            return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(baseResponseDto, HttpStatus.OK);
    }


}
