package com.rest.api.service;

import com.rest.api.authentication.AuthRequest;
import com.rest.api.authentication.AuthResponse;
import com.rest.api.authentication.RegisterRequest;
import com.rest.api.config.JwtService;
import com.rest.api.entity.User;
import com.rest.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .city(request.getCity())
                .company(request.getCompany())
                .build();
        this.userRepository.save(user);
        var jwtToken = this.jwtService.generateToken(new HashMap<>(), user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        var user = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = this.jwtService.generateToken(new HashMap<>(), user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
