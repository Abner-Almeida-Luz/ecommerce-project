package com.AbnerLuz.ecommerce_project.services;

import com.AbnerLuz.ecommerce_project.core.*;
import com.AbnerLuz.ecommerce_project.dtos.OrderItemResponse;
import com.AbnerLuz.ecommerce_project.dtos.OrderResponse;
import com.AbnerLuz.ecommerce_project.exceptions.ResourceNotFoundException;
import com.AbnerLuz.ecommerce_project.repositories.CartRepository;
import com.AbnerLuz.ecommerce_project.repositories.OrderItemsRepository;
import com.AbnerLuz.ecommerce_project.repositories.OrderRepository;
import com.AbnerLuz.ecommerce_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderResponse toResponseOrder(Orders order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                order.getOrderItems().stream().map(this::toResponseOrderItem).toList()
        );
    }

    public OrderItemResponse toResponseOrderItem(OrderItems orderItem) {
        return new OrderItemResponse(orderItem.getId(),orderItem.getProduct().getId(),orderItem.getProduct().getName(),orderItem.getQuantity(),orderItem.getTotal());
    }

    public OrderResponse checkout(Long cartId) {
        Carts cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        Orders order = new Orders(cart.getUser(),OrderStatus.PAID,BigDecimal.ZERO);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItems item : cart.getCartItems()){
            if (item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) != item.getTotal()){
                throw new RuntimeException("There's any invalid Cart item");
            } else {
                item.getProduct().setStock(item.getProduct().getStock() -= item.getQuantity());
                total = total.add(item.getTotal());
                OrderItems orderItem = new OrderItems(order,item.getProduct(),item.getQuantity(),item.getTotal());
                order.getOrderItems().add(orderItem);
            }
        }
        order.setTotal(total);
    }

    public List<OrderResponse> listOrders(String login) {
        Users user = userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        return user.getOrders().stream().map(this::toResponseOrder).toList();
    }
}
