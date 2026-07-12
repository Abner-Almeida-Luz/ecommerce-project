package com.AbnerLuz.ecommerce_project.repositories;

import com.AbnerLuz.ecommerce_project.core.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Long> { }
