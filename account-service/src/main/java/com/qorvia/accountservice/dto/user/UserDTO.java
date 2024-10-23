package com.qorvia.accountservice.dto.user;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.user.UserStatus;
import com.qorvia.accountservice.model.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Roles role;
    private VerificationStatus verificationStatus;
    private UserStatus status;
    private String pro_img;
    private Boolean isGoogleAuth;

}
