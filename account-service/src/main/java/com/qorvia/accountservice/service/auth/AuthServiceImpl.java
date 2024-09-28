package com.qorvia.accountservice.service.auth;

import com.qorvia.accountservice.client.NotificationClient;
import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.organizer.OrganizerLoginRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.request.LoginRequest;
import com.qorvia.accountservice.dto.request.OtpRequest;
import com.qorvia.accountservice.dto.request.RegisterRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.response.OtpResponse;
import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.model.user.UserStatus;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.repository.AddressRepository;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.UserRepository;
import com.qorvia.accountservice.service.jwt.JwtService;
import com.qorvia.accountservice.utils.ResponseUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationClient notificationClient;
    private final AuthenticationManager authenticationManager;
    private final OrganizerRepository organizerRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserDTO createAccount(RegisterRequest request) {
        Optional<UserInfo> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent() && existingUser.get().getIsGoogleAuth()) {
            throw new IllegalArgumentException("This email is already registered with Google authentication. Please log in with Google.");
        }

        UserInfo userInfo = UserInfo.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Roles.USER)
                .status(UserStatus.ACTIVE)
                .verificationStatus(VerificationStatus.PENDING)
                .isGoogleAuth(false)
                .build();

        UserInfo savedUser = userRepository.save(userInfo);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(request.getUsername());
        userDTO.setEmail(request.getEmail());
        userDTO.setRole(Roles.USER);
        userDTO.setStatus(UserStatus.ACTIVE);
        userDTO.setVerificationStatus(VerificationStatus.PENDING);
        notificationClient.sendOtp(request.getEmail());

        return userDTO;
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> loginUser(LoginRequest loginRequest, HttpServletResponse response) {
        Optional<UserInfo> userInfo = userRepository.findByEmail(loginRequest.getEmail());
        if (!userInfo.isPresent()) {
            return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "User not found", null);
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            final String jwtToken = jwtService.generateTokenForUser(loginRequest.getEmail());

         if (!userInfo.get().getVerificationStatus().equals(VerificationStatus.PENDING)) {
            setCookieInResponse(response,jwtToken);
         }

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userInfo.get().getUsername());
            userDTO.setEmail(userInfo.get().getEmail());
            userDTO.setRole(userInfo.get().getRoles());
            userDTO.setVerificationStatus(userInfo.get().getVerificationStatus());
            userDTO.setStatus(userInfo.get().getStatus());
            if (userInfo.get().getProfileImg() != null){
                userDTO.setPro_img(userInfo.get().getProfileImg());
            }

            return ResponseUtil.buildResponse(HttpStatus.OK, "Login successful", userDTO);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseUtil.buildResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", null);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<OtpResponse>> verifyEmail(OtpRequest otpRequest,HttpServletResponse servletResponse) {
        if (!userRepository.existsByEmail(otpRequest.getEmail())) {
            return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, "Email does not exist", null);
        }
        try {
            ResponseEntity<ApiResponse<Boolean>> response = notificationClient.verifyOtp(
                    otpRequest.getEmail(), otpRequest.getOtp());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().getData()) {
                UserInfo userInfo = userRepository.findByEmail(otpRequest.getEmail()).get();
                userInfo.setVerificationStatus(VerificationStatus.VERIFIED);
                userRepository.save(userInfo);

                OtpResponse otpResponse = new OtpResponse();
                otpResponse.setEmail(otpRequest.getEmail());
                otpResponse.setVerificationStatus(VerificationStatus.VERIFIED);
                final String jwtToken = jwtService.generateTokenForUser(otpResponse.getEmail());

                setCookieInResponse(servletResponse,jwtToken);

                return ResponseUtil.buildResponse(HttpStatus.OK, "Email verified successfully", otpResponse);
            } else {
                String errorMessage = "Email verification failed, try again.";
                return ResponseUtil.buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, errorMessage, null);
            }
        } catch (Exception e) {
            String errorMessage = "Email verification failed: " + e.getMessage();
            return ResponseUtil.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    @Override
    @Transactional
    public String googleAuthentication(UserDTO userDTO, HttpServletResponse response) throws Exception {
        String token;
        System.out.println(userRepository.existsByEmail(userDTO.getEmail()) + " email exists or not");
        Optional<UserInfo> existingUser = userRepository.findByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            if (existingUser.get().getIsGoogleAuth()) {
                token = jwtService.generateTokenForUser(userDTO.getEmail());
            } else {
                throw new IllegalArgumentException("Email already registered, please log in using your credentials.");
            }
        } else {
            UserInfo user = new UserInfo();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            String randomPassword = generateRandomPassword(10);
            user.setPassword(passwordEncoder.encode(randomPassword));
            user.setRoles(Roles.USER);
            user.setVerificationStatus(VerificationStatus.VERIFIED);
            user.setStatus(UserStatus.ACTIVE);
            user.setIsGoogleAuth(true);
            userRepository.save(user);

            token = jwtService.generateTokenForUser(userDTO.getEmail());
        }

        log.info("token: {}", token);
        setCookieInResponse(response, token);
        return "Google auth success";
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseUtil.buildResponse(HttpStatus.OK, "Logout successful", null);
        } catch (Exception e) {
            return ResponseUtil.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Logout failed: " + e.getMessage(), null);
        }
    }

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
                .roles(Roles.ORGANIZER)
                .build();

        organizerRepository.save(organizer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Organizer registered successfully", "Organizer ID: " + organizer.getId()));
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizerDTO>> loginOrganizer(OrganizerLoginRequest loginRequest, HttpServletResponse response) {
        Optional<Organizer> organizerInfo = organizerRepository.findByEmail(loginRequest.getEmail());
        log.info("organizer is getting in optional : .................................///// {}", organizerInfo.get().getEmail());
        if (!organizerInfo.isPresent()) {
            return ResponseUtil.buildResponse(HttpStatus.NOT_FOUND, "Organizer not found", null);
        }
        try {
            log.info("Authentication using user, pass is starting /////////////////////////////");
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            log.info("Authentication using user, pass is ending [[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
            final String jwtToken = jwtService.generateTokenForOrganizer(loginRequest.getEmail());
            log.info("jwt is generating //////////////// : {}", jwtToken);
            setCookieInResponse(response, jwtToken);

            OrganizerDTO organizerDTO = OrganizerDTO.builder()
                    .name(organizerInfo.get().getOrganizationName())
                    .email(organizerInfo.get().getEmail())
                    .verificationStatus(organizerInfo.get().getVerificationStatus())
                    .registerRequestStatus(organizerInfo.get().getRegistrationStatus())
                    .status(organizerInfo.get().getStatus())
                    .role(organizerInfo.get().getRoles())
                    .build();

            return ResponseUtil.buildResponse(HttpStatus.OK, "Login successful", organizerDTO);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseUtil.buildResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", null);
        }
    }


    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    private void setCookieInResponse(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
    }

}
