package com.example.ecommerce.auth_service.application.usecase;

import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.port.in.RegisterUserPort;
import com.example.ecommerce.auth_service.domain.port.out.PasswordHasherPort;
import com.example.ecommerce.auth_service.domain.port.out.UserRepositoryPort;
import com.example.ecommerce.auth_service.domain.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserPort {
    @Autowired
    private final UserRepositoryPort userRepository;
    @Autowired
    private final PasswordHasherPort passwordHasher;

    public RegisterUserUseCaseImpl(UserRepositoryPort userRepository, PasswordHasherPort passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }
    @Override
    public User register(String email, String rawPassword, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = User.register(email, rawPassword, role, passwordHasher);
        return userRepository.save(user);

    }
}
