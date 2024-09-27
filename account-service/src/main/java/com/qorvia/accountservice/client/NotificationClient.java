package com.qorvia.accountservice.client;

import com.qorvia.accountservice.dto.request.OtpRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationClient {

    @PostMapping("/notification/sendOtp")
    ResponseEntity<ApiResponse<String>> sendOtp(@RequestParam("email") String email);

    @PostMapping("/notification/verifyOtp")
    ResponseEntity<ApiResponse<Boolean>> verifyOtp(@RequestParam("email") String email,@RequestParam("otp") String otp);
}