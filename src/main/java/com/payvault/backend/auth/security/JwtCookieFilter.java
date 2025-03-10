package com.payvault.backend.auth.security;

import com.payvault.backend.auth.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public JwtCookieFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();

        if (jwtCookie.isPresent()) {
            String token = jwtCookie.get().getValue();
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String storedToken = (String) redisTemplate.opsForValue().get("SESSION:" + username);

                if (storedToken != null && storedToken.equals(token)) {
                    UserDetails userDetails = User.withUsername(username).password("").roles("USER").build();

                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
