package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Long> {
    boolean existsByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

}
