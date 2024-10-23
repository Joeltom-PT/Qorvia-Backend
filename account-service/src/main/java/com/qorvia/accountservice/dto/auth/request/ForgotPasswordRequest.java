package com.qorvia.accountservice.dto.auth.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
}
