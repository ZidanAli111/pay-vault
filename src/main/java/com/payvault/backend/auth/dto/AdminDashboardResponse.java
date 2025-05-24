package com.payvault.backend.auth.dto;

import java.time.LocalDateTime;

public record AdminDashboardResponse(
        String message,
        LocalDateTime timestamp,
        String adminInfo
) {
    // Static factory method for convenience
    public static AdminDashboardResponse of(String message) {
        return new AdminDashboardResponse(
                message,
                LocalDateTime.now(),
                "System Admin Dashboard"
        );
    }
}