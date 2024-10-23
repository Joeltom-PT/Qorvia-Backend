package com.qorvia.accountservice.service.user;

import com.qorvia.accountservice.dto.admin.response.GetAllUsersResponse;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.user.request.PasswordResetRequest;
import com.qorvia.accountservice.dto.user.request.ProfileChangeRequest;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserDTO getUserDataByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userInfo -> UserDTO.builder()
                        .username(userInfo.getUsername())
                        .email(userInfo.getEmail())
                        .role(userInfo.getRoles())
                        .verificationStatus(userInfo.getVerificationStatus())
                        .status(userInfo.getStatus())
                        .pro_img(userInfo.getProfileImg())
                        .isGoogleAuth(userInfo.getIsGoogleAuth())
                        .build())
                .orElse(null);
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetRequest resetRequest) {
        try {
            UserInfo user = userRepository.findByEmail(resetRequest.getEmail()).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email: " + resetRequest.getEmail())
            );
            if (passwordEncoder.matches(resetRequest.getCurrentPass(), user.getPassword())) {
                String newEncodedPassword = passwordEncoder.encode(resetRequest.getNewPass());
                user.setPassword(newEncodedPassword);
                userRepository.save(user);
                return ResponseEntity.ok("Password reset successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUserProfile(ProfileChangeRequest profileChangeRequest) {
        try {
            Optional<UserInfo> userOpt = userRepository.findByEmail(profileChangeRequest.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + profileChangeRequest.getEmail());
            }
            UserInfo user = userOpt.get();
            user.setUsername(profileChangeRequest.getUsername() != null ? profileChangeRequest.getUsername() : user.getUsername());
            user.setAddress(profileChangeRequest.getAddress() != null ? profileChangeRequest.getAddress() : user.getAddress());
            user.setAbout(profileChangeRequest.getAbout() != null ? profileChangeRequest.getAbout() : user.getAbout());
            user.setProfileImg(profileChangeRequest.getProfile_img() != null ? profileChangeRequest.getProfile_img() : user.getProfileImg());
            userRepository.save(user);
            return ResponseEntity.ok("Profile updated successfully.");
        } catch (DataAccessException e) {
            log.error("Error updating user profile for email: {}", profileChangeRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating profile.");
        }
    }



}