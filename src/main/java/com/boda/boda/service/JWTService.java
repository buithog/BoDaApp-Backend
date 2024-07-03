package com.boda.boda.service;

import com.boda.boda.dto.JwtAuthenticationResponse;
import com.boda.boda.dto.RefreshTokenRequest;
import com.boda.boda.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface JWTService {
    String extractUsername(String jwt);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token , UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

}
