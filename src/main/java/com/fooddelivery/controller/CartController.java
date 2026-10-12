package com.fooddelivery.controller;

import com.fooddelivery.dto.AddToCartRequest;
import com.fooddelivery.dto.CartItemDTO;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.CartService;
import com.fooddelivery.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart() {
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(cartService.getCartForUser(user));
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addToCart(@Valid @RequestBody AddToCartRequest req) {
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(cartService.addToCart(user, req));
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<CartItemDTO> updateQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(cartService.updateQuantity(user, itemId, quantity));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long itemId) {
        User user = userService.getCurrentAuthenticatedUser();
        cartService.removeFromCart(user, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        User user = userService.getCurrentAuthenticatedUser();
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}
