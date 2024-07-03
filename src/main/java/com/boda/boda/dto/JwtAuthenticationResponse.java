package com.boda.boda.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String token;
    private String refreshToken;
}
