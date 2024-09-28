package com.qorvia.accountservice.dto.organizer;

import lombok.Data;

@Data
public class OrganizerLoginRequest {
    private String email;
    private String password;
}
