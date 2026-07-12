package com.AbnerLuz.ecommerce_project.repositories;

import com.AbnerLuz.ecommerce_project.core.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> { }
