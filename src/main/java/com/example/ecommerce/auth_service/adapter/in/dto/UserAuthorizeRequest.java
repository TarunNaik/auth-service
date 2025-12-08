package com.example.ecommerce.auth_service.adapter.in.dto;

public record UserAuthorizeRequest(String token, String requiredRole) {
}
