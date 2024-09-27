package com.qorvia.notificationservice.controller;

import com.qorvia.notificationservice.dto.request.ResendOtpRequest;
import com.qorvia.notificationservice.dto.response.ApiResponse;
import com.qorvia.notificationservice.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Slf4j
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/sendOtp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestParam String email) {
        log.info("Send otp method is called");
        try {
            otpService.setOtp(email);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OTP sent successfully", null));
        } catch (Exception e) {
            log.error("Error sending OTP", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to send OTP", null));
        }
    }

    @PostMapping("/resendOtp")
    public ResponseEntity<ApiResponse<String>> resendOtp(@RequestBody ResendOtpRequest request) {
        log.info("Otp resend successful");
        try {
            otpService.resendOtp(request);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OTP resend successful", null));
        } catch (Exception e) {
            log.error("Error resending OTP", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to resend OTP", null));
        }
    }

    @PostMapping("/verifyOtp")
    ResponseEntity<ApiResponse<Boolean>> verifyOtp(@RequestParam("email") String email,@RequestParam("otp") String otp){
        log.info("Verify OTP method is called. OTP: {}, Email: {}", otp, email);
        try {
            boolean isVerified = otpService.verifyOtp(email,otp);
            log.info("Verification status of the email : {}", isVerified);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OTP verification successful", isVerified));
        } catch (Exception e) {
            log.error("Error verifying OTP", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to verify OTP", false));
        }
    }
}
