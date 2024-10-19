package com.innovative.coder.aqua;

import com.innovative.coder.aqua.Model.Company;
import com.innovative.coder.aqua.Model.Login;
import com.innovative.coder.aqua.Model.User;
import com.innovative.coder.aqua.Repository.LoginRepository;
import com.innovative.coder.aqua.applicationData.ApplicationConstants;
import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUtils {
    @Autowired
    private LoginRepository loginRepository;
    public Login tokenChecking(HttpServletRequest request){
            String header=request.getHeader(ApplicationConstants.AUTHORIZATION);
            Assert.notNull(header, ApplicationConstants.NO_TOKEN);
            String authToken = header.replace(ApplicationConstants.TOKEN_PREFIX, "").trim();
            Login login =loginRepository.findByTokenAndStatus(authToken, ApplicationEnums.LogInStatusEnum.LOGGED_IN);
            Assert.notNull(login,ApplicationConstants.LOGIN_NOT_FOUND);
            User user =login.getUser();
            Company company = user.getCompany();
            Assert.isTrue(!user.getIsDeleted(), ApplicationConstants.USER_NOT_FOUND);
//            Assert.isTrue(!company.getIsDeleted(), ApplicationConstants.COMPANY_NOT_FOUND);
            return login;
    }

}
