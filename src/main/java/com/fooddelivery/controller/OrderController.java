package com.fooddelivery.controller;

import com.fooddelivery.dto.CreateOrderRequest;
import com.fooddelivery.dto.OrderResponseDTO;
import com.fooddelivery.dto.OrderStatusUpdateRequest;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderRequest req) {
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(orderService.createOrder(user, req));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getRestaurantOrders(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getRestaurantOrders(restaurantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest req) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, req.getStatus(), req.getPaymentStatus()));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(orderService.cancelOrder(user, id));
    }
}
