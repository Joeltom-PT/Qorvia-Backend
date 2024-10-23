package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.dto.user.request.PasswordResetRequest;
import com.qorvia.accountservice.dto.user.request.ProfileChangeRequest;
import com.qorvia.accountservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/user/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @GetMapping("/getUserData")
    public UserDTO getUserData(@RequestParam String email) {
        return userService.getUserDataByEmail(email);
    }

    @PutMapping("/passwordReset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest){
        log.info("password resetting by the user : {}", resetRequest.getEmail());
        return userService.resetPassword(resetRequest);
    }

    @PutMapping("/changeProfileInfo")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileChangeRequest profileChangeRequest){
        log.info("updating the user profile by the user : {}", profileChangeRequest.getEmail());
     return userService.updateUserProfile(profileChangeRequest);
    }



}
