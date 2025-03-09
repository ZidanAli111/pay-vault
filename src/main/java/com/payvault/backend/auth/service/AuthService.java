package com.payvault.backend.auth.service;

import com.payvault.backend.auth.entity.Role;
import com.payvault.backend.auth.entity.User;
import com.payvault.backend.auth.repository.UserRepository;
import com.payvault.backend.auth.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void registerUser(String username, String password, HttpServletResponse response) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        userRepository.save(user);

       String token= jwtUtil.generateToken(username);

       setJwtCookie(response,token);
    }

    public void authenticateUser(String username, String password,HttpServletResponse response) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token= jwtUtil.generateToken(username);
        setJwtCookie(response,token);
    }
    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

}
