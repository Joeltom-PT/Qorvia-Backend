package com.qorvia.eventmanagementservice.service;

import com.qorvia.eventmanagementservice.dto.EventCategoryDTO;
import com.qorvia.eventmanagementservice.dto.request.EventCategoryRequest;
import com.qorvia.eventmanagementservice.dto.response.GetAllCategoriesResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface EventService {
    ResponseEntity<?> categoryRequest(EventCategoryRequest categoryRequest);

    ResponseEntity<?> changeCategoryStatus(UUID id, String status);

    ResponseEntity<GetAllCategoriesResponse> getAllEventCategories(int page, int size, String search, String status);

}
