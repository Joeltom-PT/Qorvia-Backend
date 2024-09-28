package com.qorvia.accountservice.service.user;

import com.qorvia.accountservice.dto.admin.response.GetAllUsersResponse;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO getUserDataByEmail(String email) {
        Optional<UserInfo> optionalUserInfo = userRepository.findByEmail(email);

        if (optionalUserInfo.isPresent()) {
            UserInfo userInfo = optionalUserInfo.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userInfo.getUsername());
            userDTO.setEmail(userInfo.getEmail());
            userDTO.setRole(userInfo.getRoles());
            userDTO.setVerificationStatus(userInfo.getVerificationStatus());
            userDTO.setStatus(userInfo.getStatus());
            if (userInfo.getProfileImg() != null){
                userDTO.setPro_img(userInfo.getProfileImg());
            }
            return userDTO;
        }
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse<GetAllUsersResponse>> getAllUsers(Pageable pageable) {
        try {
            Page<UserInfo> userPage = userRepository.findAll(pageable);

            if (userPage == null || !userPage.hasContent()) {
                ApiResponse<GetAllUsersResponse> emptyResponse = new ApiResponse<>(
                        HttpStatus.NOT_FOUND.value(),
                        "No users found.",
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyResponse);
            }
            List<UserDTO> userDTOs = userPage.getContent().stream()
                    .map(user -> UserDTO.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .role(user.getRoles())
                            .verificationStatus(user.getVerificationStatus())
                            .status(user.getStatus())
                            .pro_img(user.getProfileImg())
                            .build())
                    .collect(Collectors.toList());

            GetAllUsersResponse usersResponse = new GetAllUsersResponse(
                    userDTOs,
                    userPage.getTotalElements(),
                    userPage.getTotalPages(),
                    userPage.getNumber(),
                    userPage.getSize()
            );

            ApiResponse<GetAllUsersResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "User list retrieved successfully.",
                    usersResponse
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.error("Error fetching users: ", e);

            ApiResponse<GetAllUsersResponse> errorResponse = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while fetching users: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
