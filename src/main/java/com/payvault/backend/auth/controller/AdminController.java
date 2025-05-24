package com.payvault.backend.auth.controller;

import com.payvault.backend.auth.dto.AdminDashboardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Operation(summary = "Get admin dashboard", security = @SecurityRequirement(name = "JWT"))
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDashboardResponse getAdminDashboard() {
        return AdminDashboardResponse.of("Welcome to the admin Dashboard!");
    }
}