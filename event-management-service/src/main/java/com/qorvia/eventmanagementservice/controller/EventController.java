package com.qorvia.eventmanagementservice.controller;

import com.qorvia.eventmanagementservice.dto.EventCategoryDTO;
import com.qorvia.eventmanagementservice.dto.request.EventCategoryRequest;
import com.qorvia.eventmanagementservice.dto.response.GetAllCategoriesResponse;
import com.qorvia.eventmanagementservice.security.RequireRole;
import com.qorvia.eventmanagementservice.security.Roles;
import com.qorvia.eventmanagementservice.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @RequireRole(role = Roles.ORGANIZER)
    @PostMapping("/categoryRequest")
    public ResponseEntity<?> categoryRequest(@RequestBody EventCategoryRequest categoryRequest){
        return eventService.categoryRequest(categoryRequest);
    }

    @RequireRole(role = Roles.ADMIN)
    @GetMapping("/getAllEventCategories")
    public ResponseEntity<GetAllCategoriesResponse> getAllEventCategories(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          @RequestParam("search") String search,
                                                                          @RequestParam("status") String status){
        return eventService.getAllEventCategories(page,size,search,status);
    }

    @RequireRole(role = Roles.ADMIN)
    @PutMapping("/changeCategoryStatus/{id}")
    public ResponseEntity<?> changeCategoryStatus(@PathVariable("id") UUID id, @RequestParam String status){
        return eventService.changeCategoryStatus(id, status);
    }


//    @RequireRole(role = Roles.ORGANIZER)
//    @PostMapping("/createOnlineEvent")
//    public ResponseEntity<?> createOnlineEvent(@RequestBody ){
//        return eventService.createOnlineEvent();
//    }

}
