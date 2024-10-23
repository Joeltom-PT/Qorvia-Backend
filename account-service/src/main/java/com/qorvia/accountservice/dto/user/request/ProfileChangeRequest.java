package com.qorvia.accountservice.dto.user.request;

import lombok.Data;

@Data
public class ProfileChangeRequest {
    private String email;
    private String username;
    private String about;
    private String address;
    private String profile_img;
}
