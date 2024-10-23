package com.qorvia.accountservice.dto.admin.response;

import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.user.UserDTO;
import com.qorvia.accountservice.model.VerificationStatus;
import com.qorvia.accountservice.model.organizer.OrganizerStatus;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import lombok.Data;

import java.util.List;

@Data
public class GetAllOrganizersResponse {
    private List<OrganizerDTO> organizers;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
