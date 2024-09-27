package com.qorvia.notificationservice.service;

import com.qorvia.notificationservice.dto.request.ResendOtpRequest;

public interface OtpService {
    void setOtp(String email);
    boolean verifyOtp(String email, String otp);

    void resendOtp(ResendOtpRequest request);
}