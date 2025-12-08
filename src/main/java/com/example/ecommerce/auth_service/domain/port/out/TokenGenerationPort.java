package com.example.ecommerce.auth_service.domain.port.out;

import com.example.ecommerce.auth_service.domain.entity.User;

import java.util.Optional;

public interface TokenGenerationPort {
    String generateToken(User user);
    Optional<String> getSubjectFromToken(String token);
    Optional<String> getClaimFromToken(String token, String claimKey);
    boolean validateToken(String token);

}
