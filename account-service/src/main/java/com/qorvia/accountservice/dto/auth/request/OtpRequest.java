package com.qorvia.accountservice.dto.auth.request;

import lombok.Data;

@Data
public class OtpRequest {

    private String otp;
    private String email;

}
