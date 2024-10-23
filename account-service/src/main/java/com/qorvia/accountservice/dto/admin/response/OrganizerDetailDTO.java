package com.qorvia.accountservice.dto.admin.response;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
public class OrganizerDetailDTO {
    private Long id;
    private String organizationName;
    private String email;
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
    private Integer totalEvents;
    private RegisterRequestStatus registrationStatus;
    private OrganizerStatus status;
    private VerificationStatus verificationStatus;
}
