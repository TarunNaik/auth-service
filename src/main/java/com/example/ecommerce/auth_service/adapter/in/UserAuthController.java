package com.example.ecommerce.auth_service.adapter.in;

import com.example.ecommerce.auth_service.adapter.in.dto.*;
import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.port.in.AuthenticateUserPort;
import com.example.ecommerce.auth_service.domain.port.in.RegisterUserPort;
import com.example.ecommerce.auth_service.domain.vo.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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
        return ResponseEntity.ok(authenticateUserPort.authenticate(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(@RequestHeader("Authorization") String token) {
        // Profile retrieval logic to be implemented
        return ResponseEntity.ok("User profile data");
    }
    @PutMapping("/profile/update")
    public ResponseEntity<String> updateProfile(@RequestBody String profileData) {
        // Profile update logic to be implemented
        return ResponseEntity.ok("User profile updated");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestBody UserAuthorizeRequest request) {
        // Token validation logic to be implemented
        return ResponseEntity.ok(authenticateUserPort.authorizeUser(request.token(), request.requiredRole()));
    }

    @GetMapping("/get-user-id")
    public ResponseEntity<Optional<UUID>> getUserIdFromToken(
            @RequestHeader("Authorization") String token,
            @RequestParam("role") String role) {
        // Get user ID from token logic to be implemented
        return ResponseEntity.ok(authenticateUserPort.getUserIdFromToken(token, role));
    }
}
