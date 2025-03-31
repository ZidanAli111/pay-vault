package com.payvault.backend.auth.security;

import com.payvault.backend.auth.entity.User;
import com.payvault.backend.auth.repository.UserRepository;
import com.payvault.backend.auth.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private static final String JWT_COOKIE_NAME = "JWT_TOKEN";

    @Autowired
    public JwtCookieFilter(JwtUtil jwtUtil, RedisTemplate<String, String> redisTemplate, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
                .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        if (jwtCookie.isPresent()) {
            String token = jwtCookie.get().getValue();
            String username = jwtUtil.extractUsername(token);

            // Validate the token before proceeding
            if (username != null && jwtUtil.validateToken(token, username)) {
                String storedToken = redisTemplate.opsForValue().get("SESSION:" + username);

                // Ensure the stored token matches the received token and authentication is not already set
                if (storedToken != null && storedToken.equals(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .roles(user.getRole().name())
                            .build();

                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
