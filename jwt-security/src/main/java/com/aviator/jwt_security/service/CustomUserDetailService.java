package com.aviator.jwt_security.service;

import com.aviator.jwt_security.model.User;
import com.aviator.jwt_security.repository.UserRepository;
import com.aviator.jwt_security.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("22 userName <loadUserByUsername -> CustomUserDetailService> >>> "  + username);
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        System.out.println("24 userName db <loadUserByUsername -> CustomUserDetailService> >>> " + user.getUserName());
        System.out.println("24 passwordHash db <loadUserByUsername -> CustomUserDetailService> >>> " + user.getPasswordHash());
        return new UserPrincipal(user);
    }
}
