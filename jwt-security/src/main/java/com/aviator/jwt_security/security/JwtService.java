package com.aviator.jwt_security.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public interface JwtService {
    // Generates a token with default claims
    String generateToken(UserDetails userDetails);

    // Generates a token with extra custom claims (like roles, permissions)
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    // Extracts the username (Subject) from the token
    String extractUsername(String token);

    // Extracts a specific claim using a resolver function
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    // Validates if the token belongs to the user and is not expired
    boolean isTokenValid(String token, UserDetails userDetails);

}
