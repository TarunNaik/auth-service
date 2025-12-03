package com.example.ecommerce.auth_service.adapter.in.dto;

public record UserRegisterRequest(String name, String email, String password, String role) {
}
