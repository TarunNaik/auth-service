package com.example.ecommerce.auth_service.adapter.in;

import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginRequest;
import com.example.ecommerce.auth_service.adapter.in.dto.UserLoginResponse;
import com.example.ecommerce.auth_service.adapter.in.dto.UserRegisterRequest;
import com.example.ecommerce.auth_service.adapter.in.dto.UserRegisterResponse;
import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.port.in.AuthenticateUserPort;
import com.example.ecommerce.auth_service.domain.port.in.RegisterUserPort;
import com.example.ecommerce.auth_service.domain.vo.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    private final RegisterUserPort registerUserPort;
    private final AuthenticateUserPort authenticateUserPort;

    public UserAuthController(RegisterUserPort registerUserPort, AuthenticateUserPort authenticateUserPort) {
        this.registerUserPort = registerUserPort;
        this.authenticateUserPort = authenticateUserPort;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest request) {

        User user = registerUserPort.register(request.name(), request.email(), request.password(), new Role(request.role()));
        return ResponseEntity.ok(new UserRegisterResponse(user.getId(), "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserLoginRequest request) {
        // Login logic to be implemented
        String token = authenticateUserPort.authenticate(request.email(), request.password());
        return ResponseEntity.ok(new UserLoginResponse(token));
    }
}
