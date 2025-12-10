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

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public boolean authorizeUser(String jwtToken, String requiredRole) {
        if(jwtToken == null || !jwtToken.startsWith("Bearer ")) {
//            throw new SecurityException("Missing or invalid Authorization header.");
            return false;
        }

        String token = jwtToken.replace("Bearer ", "");
       if(!tokenGenerationPort.validateToken(token)) {
           return false;
       }
       Optional<String> userId = tokenGenerationPort.getSubjectFromToken(token);
       Optional<String> userRole = tokenGenerationPort.getClaimFromToken(token, "role");
       User user = userRepositoryPort.findById(userId.orElseThrow()).orElseThrow();
       return userRole.isPresent() && userRole.get().equalsIgnoreCase(requiredRole);

    }

    @Override
    public Optional<UUID> getUserIdFromToken(String token, String requiredRole) {
        if(token == null || !token.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String jwtToken = token.replace("Bearer ", "");
        if(!tokenGenerationPort.validateToken(jwtToken)) {
            return Optional.empty();
        }
        Optional<String> userId = tokenGenerationPort.getSubjectFromToken(jwtToken);
        Optional<String> userRole = tokenGenerationPort.getClaimFromToken(jwtToken, "role");
        Optional<String> userEmail = tokenGenerationPort.getClaimFromToken(jwtToken, "email");
        if(userId.isEmpty() || userRole.isEmpty() || userEmail.isEmpty()) {
            return Optional.empty();
        }
        User user = userRepositoryPort.findByEmail(userEmail.get()).orElseThrow();
        if(userRole.get().equalsIgnoreCase(requiredRole)) {
            return Optional.of(user.getId());
        } else {
            return Optional.empty();
        }
    }

}
