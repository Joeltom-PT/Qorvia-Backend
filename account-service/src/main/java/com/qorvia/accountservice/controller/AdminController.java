package com.qorvia.accountservice.controller;

import com.qorvia.accountservice.dto.admin.request.BlockAndUnblockUserRequest;
import com.qorvia.accountservice.dto.admin.request.ChangeOrganizerStatusRequest;
import com.qorvia.accountservice.dto.admin.response.GetAllOrganizersResponse;
import com.qorvia.accountservice.dto.admin.response.GetAllUsersResponse;
import com.qorvia.accountservice.dto.admin.response.OrganizerDetailDTO;
import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.service.admin.AdminService;
import com.qorvia.accountservice.service.organizer.OrganizerService;
import com.qorvia.accountservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/account/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;
    private final OrganizerService organizerService;
    private final AdminService adminService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<GetAllUsersResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam("search") String search) {
        return adminService.getAllUsers(page,size,search);
    }


    @PutMapping("/blockOrUnblockUser")
    public ResponseEntity<String> blockOrUnblockUser(@RequestBody BlockAndUnblockUserRequest request){
        return adminService.blockOrUnblockUser(request.getEmail());
    }

    @GetMapping("/getAllOrganizers")
    public ResponseEntity<ApiResponse<GetAllOrganizersResponse>> getAllOrganizers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam("search") String search,
            @RequestParam("status") String approvalStatus){
        return adminService.getAllOrganizers(page,size,search,approvalStatus);
    }


    @GetMapping("/getOrganizerDetails/{id}")
    public ResponseEntity<ApiResponse<OrganizerDetailDTO>> getOrganizerDetails(@PathVariable("id") Long id){
        return adminService.getOrganizerDetails(id);
    }

    @PutMapping("/changeOrganizerStatus/{id}")
    public ResponseEntity<ApiResponse<String>> changeOrganizerStatus(@PathVariable("id") Long organizerId, @RequestBody ChangeOrganizerStatusRequest request){
        return adminService.changeOrganizerStatus(organizerId,request);
    }

}
