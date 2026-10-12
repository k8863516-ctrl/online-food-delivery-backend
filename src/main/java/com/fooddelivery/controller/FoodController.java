package com.fooddelivery.controller;

import com.fooddelivery.dto.FoodItemDTO;
import com.fooddelivery.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItemDTO>> getFoodsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(foodService.getFoodsByRestaurant(restaurantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDTO> getFoodById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @PostMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<FoodItemDTO> addFoodItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody FoodItemDTO dto) {
        return ResponseEntity.ok(foodService.addFoodItem(restaurantId, dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<FoodItemDTO> updateFoodItem(
            @PathVariable Long id,
            @RequestBody FoodItemDTO dto) {
        return ResponseEntity.ok(foodService.updateFoodItem(id, dto));
    }

    @PatchMapping("/{id}/toggle-availability")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<FoodItemDTO> toggleAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.toggleAvailability(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_RESTAURANT', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}
