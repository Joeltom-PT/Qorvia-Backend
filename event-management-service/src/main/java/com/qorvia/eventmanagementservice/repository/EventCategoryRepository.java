package com.qorvia.eventmanagementservice.repository;

import com.qorvia.eventmanagementservice.model.EventCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventCategoryRepository extends MongoRepository<EventCategory, UUID> {

    Optional<EventCategory> findByNameIgnoreCase(String name);
}
