package com.example.ecommerce.auth_service.domain.port.in;

import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginRequest;
import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginResponse;

import java.util.Optional;
import java.util.UUID;

public interface AuthenticateUserPort {
    UserLoginResponse authenticate(UserLoginRequest request);
    boolean authorizeUser(String token, String requiredRole);
    Optional<UUID> getUserIdFromToken(String token, String requiredRole);
}
