package com.qorvia.accountservice.dto.admin.request;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import lombok.Data;

@Data
public class ChangeOrganizerStatusRequest {
    private String registrationStatus;
    private String status;
}
