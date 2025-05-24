package com.payvault.backend.auth.service;

import com.payvault.backend.auth.dto.AuthResponse;
import com.payvault.backend.auth.dto.LoginRequest;
import com.payvault.backend.auth.dto.RegisterRequest;
import com.payvault.backend.auth.entity.RefreshToken;
import com.payvault.backend.auth.entity.Role;
import com.payvault.backend.auth.entity.User;
import com.payvault.backend.auth.exception.AuthenticationException;
import com.payvault.backend.auth.exception.UserAlreadyExistsException;
import com.payvault.backend.auth.repository.UserRepository;
import com.payvault.backend.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).isPresent())
            throw new UserAlreadyExistsException("User with this username or email already exists");

        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        log.info("User registered successfully: {}", user.getUsername());
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserUuid());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .message("User registered successfully")
                .build();
    }

    public AuthResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username or password");
        }

        User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        String accessToken = jwtUtil.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserUuid());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new RuntimeException("User not found");
                });
    }

    public boolean validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            return jwtUtil.isTokenValid(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }

}