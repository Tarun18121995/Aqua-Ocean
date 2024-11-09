package com.innovative.coder.aqua.ServiceImplimentation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.util.Random;

@Service
public class MobileOTPService {
    @Value("${MobileOTP.account.sid}")
    private String accountSid;
    @Value("${MobileOTP.auth.token}")
    private String authToken;

    @Value("${MobileOTP.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void initTwilio() {
        if (accountSid != null && authToken != null) {
            Twilio.init(accountSid, authToken);
        } else {
            throw new IllegalArgumentException("Account SID and Auth Token must not be null");
        }
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    public void sendOtp(String otp, String toPhoneNumber) {
        String messageBody = "Your OTP is: " + otp;

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();

        System.out.println("OTP sent successfully with SID: " + message.getSid());
    }
}
