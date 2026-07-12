package com.AbnerLuz.ecommerce_project.repositories;

import com.AbnerLuz.ecommerce_project.core.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Carts, Long> { }
