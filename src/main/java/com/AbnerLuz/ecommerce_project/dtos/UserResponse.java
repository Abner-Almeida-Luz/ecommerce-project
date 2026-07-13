package com.AbnerLuz.ecommerce_project.dtos;

import com.AbnerLuz.ecommerce_project.core.UserRole;

public record UserResponse(
    Long id,
    String username,
    String login,
    UserRole role
) { }
