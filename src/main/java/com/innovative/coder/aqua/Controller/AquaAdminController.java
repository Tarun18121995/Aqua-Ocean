package com.innovative.coder.aqua.Controller;

import com.innovative.coder.aqua.Dto.BaseResponseDto;
//import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aqua-admin")
@CrossOrigin
public class AquaAdminController {
//    @ApiOperation(value = "To invite company")
//    @PostMapping(value="/invite/company")
//    public ResponseEntity<BaseResponseDto> inviteCompany(@RequestBody InviteCompanyDto inviteCompanyDto, HttpServletRequest request) {
//        return tavaAdminService.inviteCompany(inviteCompanyDto,request);
//    }
}
