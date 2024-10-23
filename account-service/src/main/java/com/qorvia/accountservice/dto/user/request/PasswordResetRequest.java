package com.qorvia.accountservice.dto.user.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String currentPass;
    private String newPass;
}
