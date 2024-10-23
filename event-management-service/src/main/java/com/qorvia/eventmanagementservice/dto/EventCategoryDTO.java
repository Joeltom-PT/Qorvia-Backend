package com.qorvia.eventmanagementservice.dto;

import com.qorvia.eventmanagementservice.model.CategoryStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class EventCategoryDTO {
    private UUID id;

    private String name;

    private String description;

    private CategoryStatus status;
}
