package com.qorvia.accountservice.dto.auth.response;

import com.qorvia.accountservice.model.VerificationStatus;
import lombok.Data;

@Data
public class OtpResponse {
    private String email;
    private VerificationStatus verificationStatus;
}
