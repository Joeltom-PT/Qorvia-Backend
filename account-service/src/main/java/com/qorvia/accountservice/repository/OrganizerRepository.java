package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.organizer.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer,Long> {
    Boolean existsByEmail(String email);

    Optional<Organizer> findByEmail(String email);
}
