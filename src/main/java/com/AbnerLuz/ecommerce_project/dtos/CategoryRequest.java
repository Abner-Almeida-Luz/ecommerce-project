package com.AbnerLuz.ecommerce_project.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank String name,
        @NotBlank String description
) { }
