package com.AbnerLuz.ecommerce_project.services;

import com.AbnerLuz.ecommerce_project.core.*;
import com.AbnerLuz.ecommerce_project.dtos.OrderItemResponse;
import com.AbnerLuz.ecommerce_project.dtos.OrderResponse;
import com.AbnerLuz.ecommerce_project.exceptions.ResourceNotFoundException;
import com.AbnerLuz.ecommerce_project.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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

    @Transactional
    public OrderResponse checkout(Long cartId) {
        Carts cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        Orders order = new Orders(cart.getUser(),OrderStatus.PENDING,BigDecimal.ZERO);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItems item : cart.getCartItems()){
            if (item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).compareTo(item.getTotal()) != 0){
                throw new RuntimeException("There's any invalid Cart item");
            } else {
                int rowsAffected = productRepository.decrementStock(
                        item.getProduct().getId(),
                        item.getQuantity()
                );
                if (rowsAffected == 0) {
                    throw new RuntimeException("Out of stock: " + item.getProduct().getName());
                }
                total = total.add(item.getTotal());
                OrderItems orderItem = new OrderItems(order,item.getProduct(),item.getQuantity(),item.getTotal());
                order.getOrderItems().add(orderItem);
            }
        }
        order.setTotal(total);
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return toResponseOrder(order);
    }

    @Transactional
    public List<OrderResponse> listOrders(String login) {
        Users user = userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User not found with login: " + login));
        return user.getOrders().stream().map(this::toResponseOrder).toList();
    }
}
