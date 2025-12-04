package com.example.ecommerce.auth_service.domain.port.out;

import com.example.ecommerce.auth_service.domain.vo.HashedPassword;

public interface PasswordHasherPort {
    HashedPassword hash(String rawPassword);
    boolean matches(HashedPassword rawPassword, HashedPassword hashedPassword);

}
