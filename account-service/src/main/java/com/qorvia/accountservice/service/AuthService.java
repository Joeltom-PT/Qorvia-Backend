package com.qorvia.accountservice.service;

import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.request.LoginRequest;
import com.qorvia.accountservice.dto.request.OtpRequest;
import com.qorvia.accountservice.dto.request.RegisterRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.response.OtpResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    UserDTO createAccount(RegisterRequest registerRequest);

    ResponseEntity<ApiResponse<Object>> loginUser(LoginRequest loginRequest, HttpServletResponse response);

    ResponseEntity<ApiResponse<OtpResponse>> verifyEmail(OtpRequest otpRequest,HttpServletResponse response);

    String googleAuthentication(UserDTO userDTO, HttpServletResponse servletResponse) throws Exception;

    ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response);
}
