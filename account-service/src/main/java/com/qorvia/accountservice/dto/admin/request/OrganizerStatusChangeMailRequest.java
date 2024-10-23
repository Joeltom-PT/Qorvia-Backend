package com.qorvia.accountservice.dto.admin.request;

import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import lombok.Data;

@Data
public class OrganizerStatusChangeMailRequest {
    private String email;
    private String message;
    private RegisterRequestStatus registerRequestStatus;
}
