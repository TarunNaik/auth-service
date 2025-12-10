package com.example.ecommerce.auth_service.adapter.in.dto;

import java.util.UUID;

public record UserRegisterResponse(UUID userId, String message) {
}
