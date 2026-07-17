package com.AbnerLuz.ecommerce_project.dtos;

import java.util.List;

public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> items
) { }
