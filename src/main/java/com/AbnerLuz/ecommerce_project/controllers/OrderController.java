package com.AbnerLuz.ecommerce_project.controllers;

import com.AbnerLuz.ecommerce_project.dtos.OrderResponse;
import com.AbnerLuz.ecommerce_project.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<OrderResponse> checkout(@PathVariable Long cartId) {
        return ResponseEntity.ok(orderService.checkout(cartId));
    }

    @GetMapping("/list-all-user-orders/{login}")
    public ResponseEntity<List<OrderResponse>> listAllOrders(@PathVariable String login) {
        return ResponseEntity.ok(orderService.listOrders(login));
    }
}
