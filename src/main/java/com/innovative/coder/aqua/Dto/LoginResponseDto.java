package com.innovative.coder.aqua.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponseDto extends BaseResponseDto
{
    private String email;
    private String role;
    private String token;
    private String warningMessage;
    private Boolean isProfileCompleted;
    private String departmentId;
}
