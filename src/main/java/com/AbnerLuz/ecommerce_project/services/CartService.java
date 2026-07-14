package com.AbnerLuz.ecommerce_project.services;

import com.AbnerLuz.ecommerce_project.core.CartItems;
import com.AbnerLuz.ecommerce_project.core.Carts;
import com.AbnerLuz.ecommerce_project.core.Products;
import com.AbnerLuz.ecommerce_project.core.Users;
import com.AbnerLuz.ecommerce_project.dtos.CartItemResponse;
import com.AbnerLuz.ecommerce_project.dtos.CartItemsRequest;
import com.AbnerLuz.ecommerce_project.dtos.CartResponse;
import com.AbnerLuz.ecommerce_project.exceptions.ResourceNotFoundException;
import com.AbnerLuz.ecommerce_project.repositories.CartItemsRepository;
import com.AbnerLuz.ecommerce_project.repositories.CartRepository;
import com.AbnerLuz.ecommerce_project.repositories.ProductRepository;
import com.AbnerLuz.ecommerce_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartResponse toResponseCart(Carts cart) {
        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                CartItemResponse[] responseItems = cart.getCartItems().stream()
                .map(this::toResponseCartItem)
                .toArray(CartItemResponse[]::new);
        );
    }

    public CartItemResponse toResponseCartItem(CartItems cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getCart().getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getTotal()
        );
    }

    public CartResponse viewCart(String login){
        Users user =  userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        Carts cart = user.getCart();
        return toResponseCart(cart);
    }

    public CartResponse addItem(CartItemsRequest data){
        Carts cart = cartRepository.findById(data.cart_id()).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + data.cart_id()));
        Products product = productRepository.findById(data.product_id()).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + data.product_id()));
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(data.quantity()));
        CartItems cartItem = new CartItems(cart,product,data.quantity(),total);
        cartItemsRepository.save(cartItem);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
        return toResponseCart(cart);
    }

    public CartResponse removeItem(Long itemId){
        CartItems  cartItem = cartItemsRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found with id: " + itemId));
        Carts cart = cartItem.getCart();
        cartItemsRepository.deleteById(itemId);
        cartRepository.save(cart);
        return toResponseCart(cart);
    }
}
