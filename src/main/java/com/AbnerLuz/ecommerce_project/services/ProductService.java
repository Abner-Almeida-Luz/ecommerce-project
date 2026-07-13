package com.AbnerLuz.ecommerce_project.services;

import com.AbnerLuz.ecommerce_project.core.Products;
import com.AbnerLuz.ecommerce_project.dtos.ProductRequest;
import com.AbnerLuz.ecommerce_project.dtos.ProductResponse;
import com.AbnerLuz.ecommerce_project.exceptions.ResourceNotFoundException;
import com.AbnerLuz.ecommerce_project.repositories.CategoryRepository;
import com.AbnerLuz.ecommerce_project.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse toResponse(Products product) {
        return new ProductResponse(
                product.getId(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl()
        );
    }

    public ProductResponse createProduct(ProductRequest data) {
        if (!categoryRepository.findById(data.category_id()).isPresent()) {
            throw new ResourceNotFoundException("Category not found with id: " + data.category_id());
        }
        Products product = new Products(categoryRepository.findById(data.category_id()).get(), data.name(),data.description(), data.price(), data.stock(), data.image_url());
        return toResponse(productRepository.save(product));
    }

    public ProductResponse findProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return toResponse(product);
    }

    public List<ProductResponse> listAllProducts() {
        return productRepository.findAll().stream().map(
                this::toResponse).toList();
    }

    public void deleteProductById(Long id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
    }
}
