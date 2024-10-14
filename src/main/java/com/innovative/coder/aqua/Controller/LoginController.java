package com.innovative.coder.aqua.Controller;

import com.innovative.coder.aqua.Dto.AquaAdminSignupDto;
import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.LoginDto;
import com.innovative.coder.aqua.Dto.LoginResponseDto;
import com.innovative.coder.aqua.Service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user-login")
public class LoginController {
@Autowired
private LoginService loginService;
    @Operation(summary = "To create tava admin")
    @PostMapping(value="/create-admin")
    public ResponseEntity<BaseResponseDto> createAquaAdmin(@RequestBody AquaAdminSignupDto aquaAdminSignupDto) {
        return loginService.createAquaAdmin(aquaAdminSignupDto);
    }
    @Operation(summary = "To login all users")
    @PostMapping(value="/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        return loginService.login(loginDto,request);
    }
//    @ApiOperation(value = "To register company")
//    @PostMapping(value="/register/company")
//    public ResponseEntity<BaseResponseDto> registerCompany(@RequestBody CompanyDto companyDto)
//    {
//        return loginService.registerCompany(companyDto);
//    }
    @Operation(summary = "To logout all users")
    @GetMapping(value="/logout")
    public ResponseEntity<BaseResponseDto> logout(HttpServletRequest request) {
        return loginService.logout(request);
    }
}
