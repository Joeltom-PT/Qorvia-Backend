package com.qorvia.accountservice.client;

import com.qorvia.accountservice.dto.admin.request.OrganizerStatusChangeMailRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.auth.response.OrganizerVerificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationClient {

    @PostMapping("/notification/sendOtp")
    ResponseEntity<ApiResponse<String>> sendOtp(@RequestParam("email") String email);

    @PostMapping("/notification/verifyOtp")
    ResponseEntity<ApiResponse<Boolean>> verifyOtp(@RequestParam("email") String email,@RequestParam("otp") String otp);

    @PostMapping("/notification/organizerEmailVerificationRequest")
    ResponseEntity<ApiResponse<String>> organizerEmailVerificationRequest(@RequestParam("email") String email);

    @PostMapping("/notification/organizerEmailVerify")
    ResponseEntity<ApiResponse<OrganizerVerificationResponse>> organizerTokenVerify(@RequestParam("token") String token);

    @PostMapping("/notification/changeStatusMail")
    ResponseEntity<ApiResponse<String>> organizerStatusChangeMail(@RequestBody OrganizerStatusChangeMailRequest mailRequest);
}