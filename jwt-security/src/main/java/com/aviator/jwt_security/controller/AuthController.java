package com.aviator.jwt_security.controller;

import com.aviator.jwt_security.dto.AdminRegisterRequest;
import com.aviator.jwt_security.dto.AuthRequest;
import com.aviator.jwt_security.dto.AuthResponse;
import com.aviator.jwt_security.dto.RegisterRequest;
import com.aviator.jwt_security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        AuthResponse authResponse = authService.registerUser(registerRequest);

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest){
        AuthResponse authResponse = authService.authenticateUser(authRequest);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdminUser(@Valid @RequestBody AdminRegisterRequest adminRegisterRequest){
        AuthResponse authResponse = authService.registerAdmin(adminRegisterRequest);
        return ResponseEntity.ok().body(authResponse);
    }

}
