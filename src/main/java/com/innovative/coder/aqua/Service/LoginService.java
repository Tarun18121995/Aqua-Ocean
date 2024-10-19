package com.innovative.coder.aqua.Service;

import com.innovative.coder.aqua.Dto.AquaAdminSignupDto;
import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.LoginDto;
import com.innovative.coder.aqua.Dto.LoginResponseDto;
import com.innovative.coder.aqua.Model.Login;
import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<LoginResponseDto> login(LoginDto loginDto, HttpServletRequest request);

    ResponseEntity<BaseResponseDto> logout(HttpServletRequest request);

    Login findByTokenAndStatus(String authToken, ApplicationEnums.LogInStatusEnum toString);

    ResponseEntity<BaseResponseDto> createAquaAdmin(AquaAdminSignupDto aquaAdminSignupDto);
}
