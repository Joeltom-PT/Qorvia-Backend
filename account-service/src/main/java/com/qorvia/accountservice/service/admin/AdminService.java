package com.qorvia.accountservice.service.admin;

import com.qorvia.accountservice.dto.admin.request.ChangeOrganizerStatusRequest;
import com.qorvia.accountservice.dto.admin.response.GetAllOrganizersResponse;
import com.qorvia.accountservice.dto.admin.response.GetAllUsersResponse;
import com.qorvia.accountservice.dto.admin.response.OrganizerDetailDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<ApiResponse<GetAllUsersResponse>> getAllUsers(int page, int size, String search);

    ResponseEntity<String> blockOrUnblockUser(String email);

    ResponseEntity<ApiResponse<GetAllOrganizersResponse>> getAllOrganizers(int page, int size, String search, String approvalStatus);

    ResponseEntity<ApiResponse<OrganizerDetailDTO>> getOrganizerDetails(Long id);

    ResponseEntity<ApiResponse<String>> changeOrganizerStatus(Long organizerId,ChangeOrganizerStatusRequest request);
}
