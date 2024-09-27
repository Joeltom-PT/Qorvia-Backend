package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.organizer.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer,Long> {
    Boolean existsByEmail(String email);
}
