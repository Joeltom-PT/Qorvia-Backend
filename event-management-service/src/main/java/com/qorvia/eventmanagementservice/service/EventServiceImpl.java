package com.qorvia.eventmanagementservice.service;

import com.qorvia.eventmanagementservice.dto.EventCategoryDTO;
import com.qorvia.eventmanagementservice.dto.request.EventCategoryRequest;
import com.qorvia.eventmanagementservice.dto.response.GetAllCategoriesResponse;
import com.qorvia.eventmanagementservice.model.CategoryStatus;
import com.qorvia.eventmanagementservice.model.EventCategory;
import com.qorvia.eventmanagementservice.repository.EventCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService{

    private final EventCategoryRepository eventCategoryRepository;

    @Override
    public ResponseEntity<?> categoryRequest(EventCategoryRequest categoryRequest) {
        try {
            if (categoryRequest.getName() == null || categoryRequest.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Category name cannot be null or empty");
            }
            if (categoryRequest.getDescription() == null || categoryRequest.getDescription().isEmpty()) {
                return ResponseEntity.badRequest().body("Category description cannot be null or empty");
            }

            Optional<EventCategory> existingCategoryOpt = eventCategoryRepository.findByNameIgnoreCase(categoryRequest.getName());

            if (existingCategoryOpt.isPresent()) {
                EventCategory existingCategory = existingCategoryOpt.get();
                if (existingCategory.getStatus() == CategoryStatus.REJECTED ||
                        existingCategory.getStatus() == CategoryStatus.INACTIVE) {
                    existingCategory.setStatus(CategoryStatus.PENDING);
                    eventCategoryRepository.save(existingCategory);
                }
                return ResponseEntity.ok("Category already exists.");
            }

            EventCategory eventCategory = EventCategory.builder()
                    .id(UUID.randomUUID())
                    .name(categoryRequest.getName())
                    .description(categoryRequest.getDescription())
                    .status(CategoryStatus.PENDING)
                    .build();

            EventCategory savedCategory = eventCategoryRepository.save(eventCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the category: " + e.getMessage());
        }
    }



    @Override
    public ResponseEntity<?> changeCategoryStatus(UUID id, String status) {
        try {
            Optional<EventCategory> optionalCategory = eventCategoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                EventCategory eventCategory = optionalCategory.get();
                eventCategory.setStatus(CategoryStatus.valueOf(status.toUpperCase()));
                EventCategory updatedCategory = eventCategoryRepository.save(eventCategory);
                return ResponseEntity.ok(updatedCategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value: " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the category status: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<GetAllCategoriesResponse> getAllEventCategories(int page, int size, String search, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        EventCategory probe = new EventCategory();

        if (status != null && !status.isEmpty()) {
            probe.setStatus(CategoryStatus.valueOf(status));
        }

        log.info("searching the values of search query: {} , and status : {}", search, status);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "description")
                .withMatcher("name", match -> match.contains().ignoreCase());

        if (search != null && !search.isEmpty()) {
            probe.setName(search);
        }

        Example<EventCategory> example = Example.of(probe, matcher);
        Page<EventCategory> eventCategoryPage = eventCategoryRepository.findAll(example, pageable);

        List<EventCategoryDTO> eventCategoryDTOs = eventCategoryPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        GetAllCategoriesResponse response = new GetAllCategoriesResponse();
        response.setCategories(eventCategoryDTOs);
        response.setTotalElements(eventCategoryPage.getTotalElements());
        response.setTotalPages(eventCategoryPage.getTotalPages());
        response.setPageNumber(eventCategoryPage.getNumber());
        response.setPageSize(eventCategoryPage.getSize());

        return ResponseEntity.ok(response);
    }

    private EventCategoryDTO convertToDTO(EventCategory eventCategory) {
        EventCategoryDTO dto = new EventCategoryDTO();
        dto.setId(eventCategory.getId());
        dto.setName(eventCategory.getName());
        dto.setDescription(eventCategory.getDescription());
        dto.setStatus(eventCategory.getStatus());
        return dto;
    }



}
