package com.aviator.jwt_security.service.impl;

import com.aviator.jwt_security.config.Constants;
import com.aviator.jwt_security.dto.AdminRegisterRequest;
import com.aviator.jwt_security.dto.AuthRequest;
import com.aviator.jwt_security.dto.AuthResponse;
import com.aviator.jwt_security.dto.RegisterRequest;
import com.aviator.jwt_security.model.Role;
import com.aviator.jwt_security.model.User;
import com.aviator.jwt_security.repository.UserRepository;
import com.aviator.jwt_security.security.JwtService;
import com.aviator.jwt_security.security.UserPrincipal;
import com.aviator.jwt_security.service.AuthService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${application.security.admin-secret}")
    private String secretKey;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Transactional
    //added this annotation for preventing any errors while transaction
    public AuthResponse registerAudienceUser(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest.getEmail(), registerRequest.getUserName());
        User user = User
                .builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .userName(registerRequest.getUserName())
                .passwordHash(passwordEncoder.encode(registerRequest.getPasswordHash()))
                .role(Role.ROLE_AUDIENCE)
                .build();
        userRepository.save(user);

        //set extraClaims
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put(Constants.JWT_CLAIM_USER_ID, user.getId());
        extraClaims.put(Constants.JWT_CLAIM_ROLE, user.getRole());

        UserDetails userDetails = new UserPrincipal(user);
        var jwtToken = jwtService.generateToken(extraClaims, userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),
                        authRequest.getPassword()
                )
        );

        // authentication succeeded. generate Token
        var user = userRepository.findByUserName(authRequest.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());              // Inject the DB ID
        extraClaims.put("role", user.getRole().name());       // Inject the Role String

        UserDetails userDetails = new UserPrincipal(user);
        var jwtToken = jwtService.generateToken(extraClaims,userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        return AuthResponse.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Transactional
    public AuthResponse registerAdmin(AdminRegisterRequest adminRegisterRequest) {
        // check if secret is same as our secret
        if (!adminRegisterRequest.getSecretKey().equals(secretKey)) {
            throw new IllegalArgumentException("Incorrect Secret Key");
        }
        // check perform validation checks
        validateRegisterRequest(adminRegisterRequest.getEmail(), adminRegisterRequest.getUserName());

        //register user
        User user = User.builder()
                .name(adminRegisterRequest.getName())
                .userName(adminRegisterRequest.getUserName())
                .email(adminRegisterRequest.getEmail())
                .passwordHash(passwordEncoder.encode(adminRegisterRequest.getPasswordHash()))
                .role(Role.ROLE_ADMIN)
                .build();
        userRepository.save(user);

        //set extraClaims
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put(Constants.JWT_CLAIM_USER_ID, user.getId());
        extraClaims.put(Constants.JWT_CLAIM_ROLE, user.getRole());

        UserDetails userDetails = new UserPrincipal(user);
        String jwtToken = jwtService.generateToken(extraClaims,userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return AuthResponse.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(String oldRefreshToken) {

        //extract the username from oldRefreshToken Claim
        String userName = jwtService.extractUsername(oldRefreshToken);

        //get the user record from DB
        // if not found throw Exception
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new UsernameNotFoundException("No User Found in the Database")
        );

        //create UserDetails of that user
        UserDetails userDetails = new UserPrincipal(user);
        //throw Exception if the token is valid and expired
        if(jwtService.isTokenValid(oldRefreshToken,userDetails)){
            throw new IllegalArgumentException("Refresh Token is invalid or Expired");
        }
        // generate new Access + refreshToken
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userid" , user.getId());
        extraClaims.put("role" , user.getRole());
        String jwt = jwtService.generateToken(extraClaims, userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return AuthResponse.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .refreshToken(refreshToken)
                .token(jwt)
                .build();
    }

    private void validateRegisterRequest(String email, String username) {
        //check admin exists
        if (userRepository.existsByUserName(username)) {
            throw new IllegalArgumentException("Admin already exists with the Username");
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
