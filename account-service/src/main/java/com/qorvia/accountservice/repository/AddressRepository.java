package com.qorvia.accountservice.repository;

import com.qorvia.accountservice.model.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<UserAddress,Long> {
}
