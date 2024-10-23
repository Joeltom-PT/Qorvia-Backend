package com.qorvia.accountservice.model.organizer;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizers")
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private BigInteger phone;

    @Column(name = "website")
    private String website;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "about", nullable = false, length = 1000)
    private String about;

    @Column(name = "total_events")
    private Integer totalEvents;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RegisterRequestStatus registrationStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganizerStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roles;
}