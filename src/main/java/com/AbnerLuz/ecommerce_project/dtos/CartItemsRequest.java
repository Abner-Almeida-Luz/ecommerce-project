package com.AbnerLuz.ecommerce_project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemsRequest(
        @NotNull @Positive Long cartItem_id,
        @NotNull @Positive Long cart_id,
        @NotNull @Positive Long product_id,
        @NotNull @Positive Integer quantity
) { }
