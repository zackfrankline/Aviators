package com.aviator.jwt_security.service.Impl;

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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public AuthResponse registerUser(RegisterRequest registerRequest) {
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

        //get jwt Token Immediately (Auto-login after Register)
//        var identifier in Java, introduced in Java 10, allows the compiler to automatically infer the type of  ]local variable based on its assigned value
        var jwtToken = jwtService.generateToken(new UserPrincipal(user));
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User getCurrentLoggedInUser() throws IllegalArgumentException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        User currentUser = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
                // Access other details like authorities, etc.
                System.out.println("Current user: " + username);
            }
        }
        if (username != null) {
            currentUser = userRepository.findByUserName(username).orElseThrow(() -> {
                        throw new IllegalArgumentException("Username for admin not found");
                    }
            );
        }
        return currentUser;
    }


    public AuthResponse authenticateUser(AuthRequest authRequest) throws AuthenticationException {
        // This validates the user/password against DB.
        // If invalid, it throws an exception automatically.
        System.out.println("Attempting login for user: " + authRequest.getUserName());
        System.out.println("Password provided: " + authRequest.getPassword());

        // 1. Fetch the user manually just for this test
        User testUser = userRepository.findByUserName(authRequest.getUserName()).get();

        // 2. Print the exact raw string you are sending
        System.out.println("RAW PASSWORD FROM POSTMAN: [" + authRequest.getPassword() + "]");

        // 3. Print the DB Hash
        System.out.println("DB HASH: [" + testUser.getPasswordHash() + "]");

        // 4. THE MOMENT OF TRUTH
        boolean isMatch = passwordEncoder.matches(authRequest.getPassword(), testUser.getPasswordHash());
        System.out.println("DOES BCRYPT MATCH? : " + isMatch);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),
                        authRequest.getPassword()
                )
        );
        System.out.println("authenticated");
        // authentication succeeded. generate Token
        var user = userRepository.findByUserName(authRequest.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        var jwtToken = jwtService.generateToken(new UserPrincipal(user));
        return AuthResponse.builder()
                .token(jwtToken)
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
                .passwordHash(adminRegisterRequest.getPasswordHash())
                .role(Role.ROLE_ADMIN)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(new UserPrincipal(user));
        return AuthResponse.builder()
                .token(jwtToken).build();
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
