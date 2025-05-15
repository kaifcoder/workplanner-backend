package com.example.workplanner.security;

import com.example.workplanner.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtils {

    private static final long JWT_EXPIRATION = 1000L * 60L * 60L * 24L * 30L * 6; // 6 months
    private SecretKey secretKey;

    @Value("${secretJwtKey}")
    private String secretJwtKey;

    @PostConstruct
    private void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretJwtKey.getBytes());
    }

    public String generateToken(Users user) {
        // Generate a JWT token using the secret key and expiration time
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey)
                .compact();

    }

    public String getUsernameFromToken(String token) {
        // Parse the JWT token and extract the username
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> getSubject) {
        return getSubject.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("Username from token: {}", getUsernameFromToken(token));
        log.info("token expiration: {}", extractClaim(token, Claims::getExpiration));
        log.info("token issued at: {}", extractClaim(token, Claims::getIssuedAt));
        log.info("is token expired: {}", isTokenExpired(token));
        // Validate the JWT token by checking if the username matches and if the token is not expired
        return (username.equals(getUsernameFromToken(token)) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        // Validate the JWT token by checking its expiration time
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
