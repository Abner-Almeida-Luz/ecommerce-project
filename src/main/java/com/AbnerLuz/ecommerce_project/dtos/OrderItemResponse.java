package com.AbnerLuz.ecommerce_project.dtos;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal total
) { }
