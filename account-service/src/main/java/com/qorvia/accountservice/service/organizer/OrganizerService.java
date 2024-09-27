package com.qorvia.accountservice.service.organizer;

import com.qorvia.accountservice.dto.organizer.OrganizerRegisterRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface OrganizerService {
    ResponseEntity<ApiResponse<String>> registerOrganizer(OrganizerRegisterRequest registerRequest);
}
