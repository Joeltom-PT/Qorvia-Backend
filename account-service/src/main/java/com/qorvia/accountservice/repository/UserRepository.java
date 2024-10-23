package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.user.UserInfo;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Long> {
    boolean existsByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

    @Query("SELECT u FROM UserInfo u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<UserInfo> findBySearch(@Param("search") String search, Pageable pageable);

}
