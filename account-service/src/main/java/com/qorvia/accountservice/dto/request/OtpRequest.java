package com.qorvia.accountservice.dto.request;

import lombok.Data;

@Data
public class OtpRequest {

    private String otp;
    private String email;

}
