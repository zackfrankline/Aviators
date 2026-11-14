package com.aviator.jwt_security.service;

import com.aviator.jwt_security.dto.AdminRegisterRequest;
import com.aviator.jwt_security.dto.AuthRequest;
import com.aviator.jwt_security.dto.AuthResponse;
import com.aviator.jwt_security.dto.RegisterRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    public AuthResponse registerAudienceUser(RegisterRequest registerRequest);
    public AuthResponse authenticateUser(AuthRequest authRequest) throws UsernameNotFoundException, AuthenticationException;
    public AuthResponse registerAdmin(AdminRegisterRequest adminRegisterRequest);
    public AuthResponse refreshToken(String oldRefreshToken);
}
