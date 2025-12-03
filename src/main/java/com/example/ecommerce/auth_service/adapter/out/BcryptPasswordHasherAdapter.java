package com.example.ecommerce.auth_service.adapter.out;

import com.example.ecommerce.auth_service.domain.port.out.PasswordHasherPort;
import com.example.ecommerce.auth_service.domain.vo.HashedPassword;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHasherAdapter implements PasswordHasherPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public HashedPassword hash(String rawPassword) {
        return new HashedPassword(encoder.encode(rawPassword));
    }
}
