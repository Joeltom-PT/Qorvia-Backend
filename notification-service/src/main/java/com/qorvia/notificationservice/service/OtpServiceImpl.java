package com.qorvia.notificationservice.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.qorvia.notificationservice.dto.request.ResendOtpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final Cache<String, String> otpCache;
    private final JavaMailSender mailSender;

    @Autowired
    public OtpServiceImpl(Cache<String, String> otpCache, JavaMailSender mailSender) {
        this.otpCache = otpCache;
        this.mailSender = mailSender;
    }

    @Override
    public void setOtp(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }
        String otp = generateOtp();
        otpCache.put(email, otp);
        sendEmail(email, otp);
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        if (email == null || otp == null) {
            throw new IllegalArgumentException("Email and OTP must not be null");
        }
        String storedOtp = otpCache.getIfPresent(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpCache.invalidate(email);
            return true;
        }
        return false;
    }

    @Override
    public void resendOtp(ResendOtpRequest request) {
        if (request.getEmail() == null) {
            throw new IllegalArgumentException("Email must not be null");
        }
        String newOtp = generateOtp();
        otpCache.put(request.getEmail(), newOtp);
        sendEmail(request.getEmail(), newOtp);
    }

    private String generateOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }
}
