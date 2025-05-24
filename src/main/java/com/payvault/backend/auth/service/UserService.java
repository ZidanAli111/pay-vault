package com.payvault.backend.auth.service;

import com.payvault.backend.auth.dto.UserResponse;
import com.payvault.backend.auth.entity.User;
import com.payvault.backend.auth.exception.UserNotFoundException;
import com.payvault.backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = (User) userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(STR."ROLE_\{user.getRole().name()}"))
        );
    }

    public UserResponse getUserById(UUID userUuid) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new UserNotFoundException(STR."User not found with id: \{userUuid}"));

        return UserResponse.builder()
                .userUuid(user.getUserUuid())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}