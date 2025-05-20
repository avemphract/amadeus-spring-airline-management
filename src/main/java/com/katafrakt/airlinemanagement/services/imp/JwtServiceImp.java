package com.katafrakt.airlinemanagement.services.imp;

import com.katafrakt.airlinemanagement.services.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Component
public class JwtServiceImp implements IJwtService {

    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NDM0NDQxMiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.I1_IpYXGgzKra4uQ-l0Q-hEZRBuAJrmu0LwBxW2HgQw
    @Value("${jwt.privateKey}") @Getter @Setter
    private String SECRET_KEY;

    @Override
    public String extractUsername(String token){
        try {
            return extractClaim(token, Claims::getSubject);
        }
        catch(ExpiredJwtException | DecodingException expiredJwtException){
            return null;
        }
    }
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    @Override
    public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact()
                ;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username  = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
