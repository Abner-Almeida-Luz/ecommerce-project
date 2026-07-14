package com.AbnerLuz.ecommerce_project.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
    @NotBlank String login,
    @NotBlank String password
) { }
