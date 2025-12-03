package com.example.ecommerce.auth_service.domain.port.in;

public interface AuthenticateUserPort {
    String authenticate(String email, String rawPassword);
}
