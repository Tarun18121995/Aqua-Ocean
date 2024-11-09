package com.innovative.coder.aqua.Controller;

import com.innovative.coder.aqua.Dto.AquaAdminSignupDto;
import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.CompanyDto;
import com.innovative.coder.aqua.Dto.LoginDto;
import com.innovative.coder.aqua.Dto.LoginResponseDto;
import com.innovative.coder.aqua.Facade.CompanyFacade;
import com.innovative.coder.aqua.Service.CompanyService;
import com.innovative.coder.aqua.Service.LoginService;
import com.innovative.coder.aqua.ServiceImplimentation.CompanyServiceImpl;
import com.innovative.coder.aqua.ServiceImplimentation.MobileOTPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(value = "/user")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MobileOTPService mobileOTPService;

    @Autowired
    private CompanyFacade companyFacade;

    @Operation(summary = "To create aqua admin")
    @PostMapping(value="/create-admin")
    public ResponseEntity<BaseResponseDto> createAquaAdmin(@RequestBody AquaAdminSignupDto aquaAdminSignupDto) {
        return loginService.createAquaAdmin(aquaAdminSignupDto);
    }

    @Operation(summary = "To login all users")
    @PostMapping(value="/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return loginService.login(loginDto,request);
    }

    @Operation(summary = "To logout all users")
    @GetMapping(value="/logout")
    public ResponseEntity<BaseResponseDto> logout(HttpServletRequest request) {
        return loginService.logout(request);
    }

    @PostMapping("/mobile-otp")
    public String sendOtp(@RequestParam String phoneNumber, HttpServletRequest request) {
        String otp = mobileOTPService.generateOtp();
        mobileOTPService.sendOtp(otp, phoneNumber);
        return "OTP sent to " + phoneNumber;
    }

    @Operation(summary = "To create company")
    @PostMapping(value="/create-company")
    public ResponseEntity<BaseResponseDto> createCompany(@RequestBody CompanyDto companyDto, HttpServletRequest request) {
        return companyFacade.createCompany(companyDto, request);
    }
}
