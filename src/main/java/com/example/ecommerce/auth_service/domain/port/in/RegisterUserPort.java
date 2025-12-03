package com.example.ecommerce.auth_service.domain.port.in;

import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.vo.Role;

public interface RegisterUserPort {
    User register(String name, String email, String rawPassword, Role role);

}
