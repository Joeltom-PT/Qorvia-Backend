package com.qorvia.accountservice.dto.auth.request;

import lombok.Data;

@Data
public class ForgotPasswordResetRequest {
    private String email;
    private String otp;
    private String password;
}
