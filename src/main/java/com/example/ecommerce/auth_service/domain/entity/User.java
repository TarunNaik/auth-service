package com.example.ecommerce.auth_service.domain.entity;

import com.example.ecommerce.auth_service.domain.port.out.PasswordHasherPort;
import com.example.ecommerce.auth_service.domain.vo.Email;
import com.example.ecommerce.auth_service.domain.vo.HashedPassword;
import com.example.ecommerce.auth_service.domain.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String name;
    private Email email;
    private HashedPassword password;
    private Role role;

    public static User register(String name, String email, String rawPassword, Role role, PasswordHasherPort passwordHasher) {
        HashedPassword hashedPassword = passwordHasher.hash(rawPassword);
        return User.builder().id(UUID.randomUUID())
                .name(name)
                .email(new Email(email))
                .password(hashedPassword)
                .role(role)
                .build();
    }
}
