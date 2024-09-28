package com.qorvia.accountservice.service.user;

import com.qorvia.accountservice.dto.admin.response.GetAllUsersResponse;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.user.UserDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Pageable;


public interface UserService {

    boolean existsByEmail(String email);

    UserDTO getUserDataByEmail(String email);

    ResponseEntity<ApiResponse<GetAllUsersResponse>> getAllUsers(Pageable pageable);
}
