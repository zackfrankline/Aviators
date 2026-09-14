package com.aviator.jwt_security.controller;

import com.aviator.jwt_security.dto.AdminRegisterRequest;
import com.aviator.jwt_security.dto.AuthRequest;
import com.aviator.jwt_security.dto.AuthResponse;
import com.aviator.jwt_security.dto.RegisterRequest;
import com.aviator.jwt_security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    //Save the new user to the database and return a success response
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        AuthResponse authResponse = authService.registerAudienceUser(registerRequest);
        ResponseCookie refreshCookie = buildRefreshCookie(authResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest){
        AuthResponse authResponse = authService.authenticateUser(authRequest);
        ResponseCookie refreshCookie = buildRefreshCookie(authResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdminUser(@Valid @RequestBody AdminRegisterRequest adminRegisterRequest){
        AuthResponse authResponse = authService.registerAdmin(adminRegisterRequest);
        ResponseCookie refreshCookie = buildRefreshCookie(authResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> getRefreshToken(@CookieValue(name = "refreshToken") String oldRefreshToken){
        AuthResponse authResponse = authService.refreshToken(oldRefreshToken);
        ResponseCookie refreshCookie = buildRefreshCookie(authResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, buildLogoutCookie().toString())
                .body("Successfully Logged Out");
    }

    private ResponseCookie buildRefreshCookie(String refreshToken){
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true) // prevents js from reading the cookie XSS (Cross site s)
                .secure(false) // to be set to true in PROD (https)
                .maxAge((7 * 24 * 60 * 60))
                .path("/")
                .sameSite("Strict")
                .build();
    }
    
    private ResponseCookie buildLogoutCookie(){
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true) // prevents js from reading the cookie XSS (Cross site s)
                .secure(false) // to be set to true in PROD (https)
                .maxAge(0)
                .path("/")
                .sameSite("Strict")
                .build();
    }

}
