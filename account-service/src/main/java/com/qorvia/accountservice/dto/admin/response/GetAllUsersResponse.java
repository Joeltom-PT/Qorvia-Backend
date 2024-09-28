package com.qorvia.accountservice.dto.admin.response;

import com.qorvia.accountservice.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersResponse {
    private List<UserDTO> users;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
}
