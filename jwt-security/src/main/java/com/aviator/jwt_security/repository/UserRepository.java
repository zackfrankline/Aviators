package com.aviator.jwt_security.repository;

import com.aviator.jwt_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmailIgnoreCase(String email);


}
