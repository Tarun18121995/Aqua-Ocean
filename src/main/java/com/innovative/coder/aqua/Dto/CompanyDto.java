package com.innovative.coder.aqua.Dto;

import com.innovative.coder.aqua.Model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {
    private String companyName;
    private String email;
    private String mobile;
    private String address1;
    private String address2;
    private String city;
    private String district;
    private String zip;
    private String state;
    private String country;
    private String companyImageUrl;
    private String industryType;
    private String password;
    private String confirmPassword;

}
