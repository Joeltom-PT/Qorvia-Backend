package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.auth.request.*;
import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.organizer.OrganizerLoginRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerVerificationTokenRequest;
import com.qorvia.accountservice.dto.auth.response.OrganizerVerificationResponse;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.auth.response.OtpResponse;
import com.qorvia.accountservice.service.auth.AuthService;
import com.qorvia.accountservice.service.user.UserService;
import com.qorvia.accountservice.service.jwt.JwtService;
import com.qorvia.accountservice.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> registerAccount(
            @RequestBody RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseUtil.buildResponse(HttpStatus.CONFLICT, "User already exists with email", null);
        }
        try {
            UserDTO userDTO = authService.createAccount(registerRequest);
            return ResponseUtil.buildResponse(HttpStatus.CREATED, "User registered successfully", userDTO);
        } catch (Exception e) {
            String errorMessage = "Registration failed: " + e.getMessage();
            return ResponseUtil.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
        }
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<ApiResponse<OtpResponse>> verifyEmail(@RequestBody OtpRequest otpRequest,
                                                                HttpServletResponse response){
        return authService.verifyEmail(otpRequest,response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        return authService.loginUser(loginRequest,response);
    }

    @PostMapping("/googleAuth")
    public ResponseEntity<?> googleAuthentication(@RequestBody UserDTO userDTO, HttpServletResponse servletResponse) {
        try {
            String response = authService.googleAuthentication(userDTO, servletResponse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            log.error("Mail address is already found: {}", userDTO.getEmail());
            return ResponseEntity.badRequest().body("Mail address is already found.");
        } catch (Exception e) {
            log.error("Google authentication failed for user: {}", userDTO.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Google authentication failed: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response) {
        return authService.logout(response);
    }

    @PostMapping("/organizerRegister")
    public ResponseEntity<ApiResponse<String>> organizerRegister(@RequestBody OrganizerRegisterRequest registerRequest){
        return authService.registerOrganizer(registerRequest);
    }

    @PostMapping("/organizerLogin")
    public ResponseEntity<ApiResponse<OrganizerDTO>> organizerLogin(@RequestBody OrganizerLoginRequest loginRequest,
                                                                    HttpServletResponse response){
        return authService.loginOrganizer(loginRequest, response);
    }

    @PostMapping("/organizerEmailVerify")
    public ResponseEntity<String> organizerEmailVerificationRequest(@RequestBody OrganizerEmailVerifySendRequest request) {
        log.info("OrganizerVerifying with email : {}", request.getEmail());
        return authService.organizerEmailVerificationSendRequest(request.getEmail());
    }

    @PostMapping("/organizerVerificationToken")
    public ResponseEntity<ApiResponse<OrganizerVerificationResponse>> organizerVerificationToken(@RequestBody OrganizerVerificationTokenRequest request, HttpServletResponse response){
        return authService.organizerVerificationTokenVerify(request,response);
    }

    @PostMapping("/forgotPasswordRequest")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        return authService.forgotPassword(forgotPasswordRequest);
    }

    @PostMapping("/forgotPasswordReset")
    public ResponseEntity<?> forgotPasswordReset(@RequestBody ForgotPasswordResetRequest passwordResetRequest){
        return authService.forgotPasswordReset(passwordResetRequest);
    }

}
