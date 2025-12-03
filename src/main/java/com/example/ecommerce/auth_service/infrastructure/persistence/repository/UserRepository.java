package com.example.ecommerce.auth_service.infrastructure.persistence.repository;

import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    boolean findByEmailAndPassword(String email, String password);
}
