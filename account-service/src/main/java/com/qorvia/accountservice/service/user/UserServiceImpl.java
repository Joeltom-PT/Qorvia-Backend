package com.qorvia.accountservice.service.user;

import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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


}
