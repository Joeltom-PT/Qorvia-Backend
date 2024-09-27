package com.qorvia.accountservice.service.organizer;

import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import com.qorvia.accountservice.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<String>> registerOrganizer(OrganizerRegisterRequest registerRequest) {
        if (organizerRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(HttpStatus.CONFLICT.value(), "Email already in use", null));
        }

        Organizer organizer = Organizer.builder()
                .organizationName(registerRequest.getOrganizationName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .website(registerRequest.getWebsite() != null ? registerRequest.getWebsite() : "")
                .address(registerRequest.getAddress())
                .address2(registerRequest.getAddress2() != null ? registerRequest.getAddress2() : "")
                .city(registerRequest.getCity())
                .country(registerRequest.getCountry())
                .state(registerRequest.getState())
                .facebook(registerRequest.getFacebook() != null ? registerRequest.getFacebook() : "")
                .instagram(registerRequest.getInstagram() != null ? registerRequest.getInstagram() : "")
                .twitter(registerRequest.getTwitter() != null ? registerRequest.getTwitter() : "")
                .linkedin(registerRequest.getLinkedin() != null ? registerRequest.getLinkedin() : "")
                .youtube(registerRequest.getYoutube() != null ? registerRequest.getYoutube() : "")
                .profileImage(registerRequest.getProfileImage())
                .about(registerRequest.getAbout())
                .status(OrganizerStatus.ACTIVE)
                .registrationStatus(RegisterRequestStatus.PENDING)
                .verificationStatus(VerificationStatus.PENDING)
                .build();

        organizerRepository.save(organizer);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Organizer registered successfully", "Organizer ID: " + organizer.getId()));
    }
}
