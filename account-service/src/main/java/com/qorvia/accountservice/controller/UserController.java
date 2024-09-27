package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/getUserData")
    public UserDTO getUserData(@RequestParam String email) {
        return userService.getUserDataByEmail(email);
    }



}
