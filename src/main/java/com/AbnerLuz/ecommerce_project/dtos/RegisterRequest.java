package com.AbnerLuz.ecommerce_project.dtos;

import com.AbnerLuz.ecommerce_project.core.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
    @NotBlank String username,
    @NotBlank String login,
    @NotBlank String password,
    @NotNull UserRole role
) { }
