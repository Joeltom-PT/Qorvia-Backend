package com.qorvia.accountservice.dto.organizer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerRegisterRequest {
    private String organizationName;
    private String email;
    private String password;
    private String repeatPassword;
    private BigInteger phone;
    private String website;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String state;
    private String facebook;
    private String instagram;
    private String twitter;
    private String linkedin;
    private String youtube;
    private String profileImage;
    private String about;
}
