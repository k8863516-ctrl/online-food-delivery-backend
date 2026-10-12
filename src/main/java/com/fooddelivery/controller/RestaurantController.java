package com.fooddelivery.controller;

import com.fooddelivery.dto.RestaurantDTO;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(restaurantService.getAllRestaurants(cuisine, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @GetMapping("/my-restaurant")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<RestaurantDTO> getMyRestaurant() {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(restaurantService.getRestaurantByOwner(currentUser));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_RESTAURANT')")
    public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantDTO dto) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(restaurantService.createRestaurant(dto, currentUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_RESTAURANT')")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantDTO dto) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
