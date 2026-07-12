package com.AbnerLuz.ecommerce_project.repositories;

import com.AbnerLuz.ecommerce_project.core.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> { }
