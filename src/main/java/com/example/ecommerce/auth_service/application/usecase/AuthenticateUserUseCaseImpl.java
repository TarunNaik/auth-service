package com.example.ecommerce.auth_service.application.usecase;

import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginRequest;
import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginResponse;
import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.port.in.AuthenticateUserPort;
import com.example.ecommerce.auth_service.domain.port.out.PasswordHasherPort;
import com.example.ecommerce.auth_service.domain.port.out.TokenGenerationPort;
import com.example.ecommerce.auth_service.domain.port.out.UserRepositoryPort;
import com.example.ecommerce.auth_service.domain.vo.HashedPassword;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserPort {

    private final UserRepositoryPort userRepositoryPort;
    private final TokenGenerationPort tokenGenerationPort;
    private final PasswordHasherPort passwordHasher;

    public AuthenticateUserUseCaseImpl(UserRepositoryPort userRepositoryPort, TokenGenerationPort tokenGenerationPort, PasswordHasherPort passwordHasher) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenGenerationPort = tokenGenerationPort;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public UserLoginResponse authenticate(UserLoginRequest request) {
        if(request == null || request.email() == null || request.password() == null) {
            throw new IllegalArgumentException("Email and password must be provided");
        }
        // Check if user exists
        User user = userRepositoryPort.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Check if password matches

        if(!passwordHasher.matches(new HashedPassword(request.password()), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = tokenGenerationPort.generateToken(user);
        return new UserLoginResponse(token, user.getName(), user.getRole().getName());
    }
}
