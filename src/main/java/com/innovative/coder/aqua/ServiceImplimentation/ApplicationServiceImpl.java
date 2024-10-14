package com.innovative.coder.aqua.ServiceImplimentation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    public Boolean isPasswordMatched(String decryptedPassword, String encryptedPassword) {
        try {
            return passwordEncoder.matches(decryptedPassword, encryptedPassword);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public String getEncryptedPassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }
}
