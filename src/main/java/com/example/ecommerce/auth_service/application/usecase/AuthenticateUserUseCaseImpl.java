package com.example.ecommerce.auth_service.application.usecase;

import com.example.ecommerce.auth_service.domain.port.in.AuthenticateUserPort;
import com.example.ecommerce.auth_service.domain.port.out.UserRepositoryPort;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserPort {

    private final UserRepositoryPort userRepositoryPort;

    public AuthenticateUserUseCaseImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public String authenticate(String email, String rawPassword) {
        // Implementation goes here
        boolean validUser = userRepositoryPort.findByEmailAndPassword(email, rawPassword);
        return null;
    }
}
