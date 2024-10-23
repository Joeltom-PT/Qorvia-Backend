package com.qorvia.accountservice.dto.organizer;

import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerDTO {
    private Long id;
    private String name;
    private String email;
    private VerificationStatus verificationStatus;
    private RegisterRequestStatus registerRequestStatus;
    private OrganizerStatus status;
    private Roles role;
}
