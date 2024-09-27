package com.qorvia.accountservice.dto.response;

import com.qorvia.accountservice.model.VerificationStatus;
import lombok.Data;

@Data
public class OtpResponse {
    private String email;
    private VerificationStatus verificationStatus;
}
