package com.innovative.coder.aqua.ServiceImplimentation;

import com.innovative.coder.aqua.Model.Company;
import com.innovative.coder.aqua.Model.Member;
import com.innovative.coder.aqua.Repository.MemberRepository;
import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncServiceImpl {
    @Autowired
    private ApplicationServiceImpl applicationService;

    @Autowired
    private MemberRepository memberRepository;

    @Async
    public void adminCreation(Company savedCompany, String password) {
        Member member = new Member();
        BeanUtils.copyProperties(savedCompany, member);
        member.setRole(ApplicationEnums.RoleEnum.ADMIN.toString());
        member.setFirstName(savedCompany.getCompanyName());
        member.setUserAddress1(savedCompany.getAddress1());
        member.setUserAddress2(savedCompany.getAddress2());
        member.setPostalCode(savedCompany.getZip());
        member.setCompany(savedCompany);
        member.setPassword(applicationService.getEncryptedPassword(password));
        memberRepository.save(member);
    }

}
