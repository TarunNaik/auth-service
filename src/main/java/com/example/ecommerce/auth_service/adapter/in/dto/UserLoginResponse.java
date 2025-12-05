package com.example.ecommerce.auth_service.adapter.in.dto;

public record UserLoginResponse(String token, String name, String role) {
}
