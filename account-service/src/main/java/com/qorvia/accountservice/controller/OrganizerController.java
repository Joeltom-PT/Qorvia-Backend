package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.organizer.OrganizerLoginRequest;
import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.service.organizer.OrganizerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/organizer")
@RequiredArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;

}
