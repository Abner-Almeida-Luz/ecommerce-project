package com.AbnerLuz.ecommerce_project.controllers;

import com.AbnerLuz.ecommerce_project.core.Users;
import com.AbnerLuz.ecommerce_project.dtos.CartItemsRequest;
import com.AbnerLuz.ecommerce_project.dtos.CartResponse;
import com.AbnerLuz.ecommerce_project.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("")
    public CartResponse cart(){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartService.viewCart(user.getLogin());
    }

    @PostMapping("/add-item")
    public CartResponse addItem(@RequestBody @Valid CartItemsRequest data){
        return cartService.addItem(data);
    }

    @DeleteMapping("/remove-item/{itemId}")
    public CartResponse addItem(@PathVariable Long itemId){
        return cartService.removeItem(itemId);
    }
}
