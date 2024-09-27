package com.qorvia.accountservice.service.user;

import com.qorvia.accountservice.dto.user.UserDTO;

public interface UserService {

    boolean existsByEmail(String email);

    UserDTO getUserDataByEmail(String email);
}
