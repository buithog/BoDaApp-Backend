package com.boda.boda.service;

import com.boda.boda.dto.JwtAuthenticationResponse;
import com.boda.boda.dto.RefreshTokenRequest;
import com.boda.boda.dto.SignInRequest;
import com.boda.boda.dto.SignUpRequest;
import com.boda.boda.entity.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
