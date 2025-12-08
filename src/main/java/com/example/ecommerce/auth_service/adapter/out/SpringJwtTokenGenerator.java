package com.example.ecommerce.auth_service.adapter.out;

import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.port.out.TokenGenerationPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class SpringJwtTokenGenerator implements TokenGenerationPort {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    @Value("${jwt.expiration-minutes}")
    private long expirationMinute;

    public SpringJwtTokenGenerator(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }


    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getId())
                .issuedAt(now)
                .expiresAt(now.plus(expirationMinute, ChronoUnit.MINUTES))
                .claim("role", user.getRole().getName()) // Assume User has getRoles()
                .claim("email", user.getEmail().getValue())
                .claim("name", user.getName())// Custom claims
                .build();

        JwtEncoderParameters params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return jwtEncoder.encode(params).getTokenValue();
    }

    @Override
    public Optional<String> getSubjectFromToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return Optional.of(jwt.getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getClaimFromToken(String token, String claimKey) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return Optional.of(jwt.getClaimAsString(claimKey));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
