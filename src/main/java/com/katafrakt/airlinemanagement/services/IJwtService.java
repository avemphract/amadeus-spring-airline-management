package com.katafrakt.airlinemanagement.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface IJwtService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> resolver);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    Claims extractAllClaims(String token);
}
