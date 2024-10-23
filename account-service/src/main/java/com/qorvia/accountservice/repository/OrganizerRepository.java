package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.organizer.RegisterRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer,Long> {
    Boolean existsByEmail(String email);

    Optional<Organizer> findByEmail(String email);

    Page<Organizer> findByOrganizationNameContainingIgnoreCase(String organizationName, Pageable pageable);

    Page<Organizer> findAllByRegistrationStatus(RegisterRequestStatus registerRequestStatus, Pageable pageable);

    Page<Organizer> findByOrganizationNameContainingIgnoreCaseAndRegistrationStatus(String organizationName, RegisterRequestStatus registerRequestStatus, Pageable pageable);
}
