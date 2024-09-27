package com.qorvia.accountservice.dto.user;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.user.UserStatus;
import com.qorvia.accountservice.model.VerificationStatus;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private Roles role;
    private VerificationStatus verificationStatus;
    private UserStatus status;
    private String pro_img;
}
