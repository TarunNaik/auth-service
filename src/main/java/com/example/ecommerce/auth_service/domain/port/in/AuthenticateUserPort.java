package com.example.ecommerce.auth_service.domain.port.in;

import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginRequest;
import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginResponse;

public interface AuthenticateUserPort {
    UserLoginResponse authenticate(UserLoginRequest request);
}
