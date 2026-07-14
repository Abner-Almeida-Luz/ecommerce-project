package com.AbnerLuz.ecommerce_project.dtos;

import java.util.ArrayList;

public record CartResponse(
    Long id,
    Long userId,
    ArrayList<CartItemResponse> items
) { }
