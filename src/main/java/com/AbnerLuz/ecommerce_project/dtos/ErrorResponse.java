package com.AbnerLuz.ecommerce_project.dtos;

import java.util.List;

public record ErrorResponse(
    int status,
    List<String> errors
) { }
