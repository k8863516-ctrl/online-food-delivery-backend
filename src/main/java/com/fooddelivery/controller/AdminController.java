package com.fooddelivery.controller;

import com.fooddelivery.dto.DashboardStatsDTO;
import com.fooddelivery.dto.OrderResponseDTO;
import com.fooddelivery.dto.RestaurantDTO;
import com.fooddelivery.dto.UserDTO;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public AdminController(OrderService orderService, UserService userService, RestaurantService restaurantService) {
        this.orderService = orderService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        return ResponseEntity.ok(orderService.getDashboardStats());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants(null, null));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
