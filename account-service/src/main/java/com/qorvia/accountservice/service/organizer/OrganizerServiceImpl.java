package com.qorvia.accountservice.service.organizer;

import com.qorvia.accountservice.dto.admin.response.GetAllOrganizersResponse;
import com.qorvia.accountservice.dto.organizer.OrganizerDTO;
import com.qorvia.accountservice.dto.response.ApiResponse;
import com.qorvia.accountservice.model.Roles;
import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.model.user.UserStatus;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.UserRepository;
import com.qorvia.accountservice.utils.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;




}
