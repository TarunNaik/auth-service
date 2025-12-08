package com.example.ecommerce.auth_service.infrastructure.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class JwtConfig {
    Logger log = org.slf4j.LoggerFactory.getLogger(JwtConfig.class);

    @Value("${jwt.secret-key}")
    private String jwtKey;

    @Bean
    public JwtEncoder jwtEncoder() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtKey);
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
       log.info("Encoding - Secret key length: {}", jwtKey.getBytes(StandardCharsets.UTF_8).length);
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtKey);
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "HmacSHA256");
        log.info("Decoding - Secret key length: {}", jwtKey.getBytes(StandardCharsets.UTF_8).length);
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

}
