package com.AbnerLuz.ecommerce_project.repositories;

import com.AbnerLuz.ecommerce_project.core.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE products p SET p.stock = p.stock - :quantity WHERE p.id = :id AND p.stock >= :quantity")
    int decrementStock(@Param("id") Long id, @Param("quantity") Integer quantity);
}
