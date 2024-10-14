package com.innovative.coder.aqua.ServiceImplimentation;

import com.innovative.coder.aqua.ApplicationUtils;
import com.innovative.coder.aqua.Configure.JwtTokenUtils;
import com.innovative.coder.aqua.Dto.AquaAdminSignupDto;
import com.innovative.coder.aqua.Dto.BaseResponseDto;
import com.innovative.coder.aqua.Dto.LoginDto;
import com.innovative.coder.aqua.Dto.LoginResponseDto;
import com.innovative.coder.aqua.Model.Login;
import com.innovative.coder.aqua.Model.User;
import com.innovative.coder.aqua.Repository.LoginRepository;
import com.innovative.coder.aqua.Repository.UserRepository;
import com.innovative.coder.aqua.Service.LoginService;
import com.innovative.coder.aqua.applicationData.ApplicationConstants;
import com.innovative.coder.aqua.applicationData.ApplicationEnums;
import com.innovative.coder.aqua.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class LoginServiceImplementation implements LoginService {
    public static final String PLEASE_PROVIDE_CORRECT_PASSWORD = "Please provide correct password";
    public static final String AQUA_ADMIN_CREATED_SUCCESSFULLY = "Aqua admin created successfully.";
    public static final String AQUA_ADMIN_ALREADY_EXISTS = "Aqua admin already exists.";
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  ApplicationServiceImpl applicationUtilityServiceImpl;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Override
    public ResponseEntity<LoginResponseDto> login(LoginDto loginDto, HttpServletRequest request) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try {
            final User user = userRepository.findByEmailAndIsDeleted(loginDto.getEmail(), Boolean.FALSE);
            Assert.notNull(user, ApplicationConstants.EMAIL_NOT_EXIST);
                    LoginResponseDto loginResponse = getLoginResponseDtoResponseEntity(loginDto, request, loginResponseDto, user);
                    if (loginResponse != null) {
                         new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
                    }else {
                        throw new ApiException(PLEASE_PROVIDE_CORRECT_PASSWORD);
                    }
        } catch (Exception e) {
            e.printStackTrace();
//            log.info(e.getLocalizedMessage());
            loginResponseDto.setMessage(e.getLocalizedMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

    }

    private LoginResponseDto getLoginResponseDtoResponseEntity(LoginDto loginDto, HttpServletRequest request, LoginResponseDto loginResponseDto, User user) {
        if (Boolean.TRUE.equals(applicationUtilityServiceImpl.isPasswordMatched(loginDto.getPassword(), user.getPassword()))) {
            if(user.getLogins().stream().noneMatch(login -> StringUtils.pathEquals(ApplicationEnums.LogInStatusEnum.LOGGED_IN.toString(), login.getStatus().toString()))) {
                Login login = new Login();
                login.setUser(user);
                login.setStatus(ApplicationEnums.LogInStatusEnum.LOGGED_IN);
                login.setLoggedInTime(LocalDateTime.now());
                login.setBrowserDetails(request.getHeader(ApplicationConstants.USER_AGENT));
                login.setToken(jwtTokenUtils.generateToken(login.getUser()));
                Login savedLogin = loginRepository.save(login);
                loginResponseDto.setEmail(savedLogin.getUser().getEmail());
                loginResponseDto.setToken(savedLogin.getToken());
                loginResponseDto.setRole(savedLogin.getUser().getRole());
                loginResponseDto.setMessage(ApplicationConstants.LOGIN_SUCCESSFUL);
                loginResponseDto.setId(savedLogin.getUser().getId());
                Assert.notNull(savedLogin.getUser(), ApplicationConstants.USER_NOT_FOUND);
                return loginResponseDto;
            }else {
                throw new ApiException(ApplicationConstants.ALREADY_LOGGED_IN);
            }
        }
        return null;
    }

    @Autowired
    private ApplicationUtils applicationUtils;
    @Override
    public ResponseEntity<BaseResponseDto> logout(HttpServletRequest request) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
       try {
           Login login = applicationUtils.tokenChecking(request);
           if(!ObjectUtils.isEmpty(login)){
               login.setStatus(ApplicationEnums.LogInStatusEnum.LOGGED_OUT);
               loginRepository.save(login);
               baseResponseDto.setMessage(ApplicationConstants.LOGOUT_SUCCESSFUL);
               return new ResponseEntity<>(baseResponseDto, HttpStatus.OK);
            }
        }catch (Exception exception) {
           exception.printStackTrace();
//            log.info(e.getLocalizedMessage());
           baseResponseDto.setMessage(exception.getLocalizedMessage());
           return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
       }
        return null;
    }

    @Override
    public Login findByTokenAndStatus(String authToken, ApplicationEnums.LogInStatusEnum status) {
        return loginRepository.findByTokenAndStatus(authToken, status);
    }

    @Override
    public ResponseEntity<BaseResponseDto> createAquaAdmin(AquaAdminSignupDto aquaAdminSignupDto) {
        BaseResponseDto baseResponseDto=new BaseResponseDto();
        try
        {
            String aquaAdmin = ApplicationEnums.RoleEnum.AQUA_ADMIN.toString();
            final User userDetails =userRepository.findByRoleAndIsDeleted(aquaAdmin, Boolean.FALSE);
            if (ObjectUtils.isEmpty(userDetails)){
                User user =new User();
                BeanUtils.copyProperties(aquaAdminSignupDto, user);
                user.setPassword(applicationUtilityServiceImpl.getEncryptedPassword(aquaAdminSignupDto.getPassword()));
                user.setIsActive(Boolean.TRUE);
                user.setRole(aquaAdmin);
                userRepository.save(user);
                baseResponseDto.setMessage(AQUA_ADMIN_CREATED_SUCCESSFULLY);
            }
            else {
                throw new ApiException(AQUA_ADMIN_ALREADY_EXISTS);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
//            log.info(e.getLocalizedMessage());
            baseResponseDto.setMessage(e.getLocalizedMessage());
            return new ResponseEntity<>(baseResponseDto,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(baseResponseDto,HttpStatus.OK);
    }
    }

