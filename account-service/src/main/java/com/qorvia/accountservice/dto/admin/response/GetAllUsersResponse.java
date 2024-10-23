package com.qorvia.accountservice.dto.admin.response;

import com.qorvia.accountservice.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
