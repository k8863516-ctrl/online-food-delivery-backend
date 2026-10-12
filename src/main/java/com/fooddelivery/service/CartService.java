package com.fooddelivery.service;

import com.fooddelivery.dto.AddToCartRequest;
import com.fooddelivery.dto.CartItemDTO;
import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.entity.User;
import com.fooddelivery.exception.BadRequestException;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;

    public CartService(CartItemRepository cartItemRepository, FoodService foodService) {
        this.cartItemRepository = cartItemRepository;
        this.foodService = foodService;
    }

    public List<CartItemDTO> getCartForUser(User user) {
        return cartItemRepository.findByUser(user).stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartItemDTO addToCart(User user, AddToCartRequest req) {
        FoodItem foodItem = foodService.findById(req.getFoodItemId());

        if (Boolean.FALSE.equals(foodItem.getIsAvailable())) {
            throw new BadRequestException("Item is currently unavailable: " + foodItem.getName());
        }

        // Check if existing items in cart are from the same restaurant
        List<CartItem> userItems = cartItemRepository.findByUser(user);
        if (!userItems.isEmpty()) {
            Long existingRestaurantId = userItems.get(0).getFoodItem().getRestaurant().getId();
            if (!existingRestaurantId.equals(foodItem.getRestaurant().getId())) {
                // Clear cart from previous restaurant and start fresh for new restaurant
                cartItemRepository.deleteByUser(user);
                cartItemRepository.flush();
            }
        }

        Optional<CartItem> existingItem = cartItemRepository.findByUserAndFoodItem(user, foodItem);
        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + req.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setFoodItem(foodItem);
            cartItem.setQuantity(req.getQuantity());
        }

        CartItem saved = cartItemRepository.save(cartItem);
        return new CartItemDTO(saved);
    }

    @Transactional
    public CartItemDTO updateQuantity(User user, Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Access denied to this cart item");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            return null;
        }

        item.setQuantity(quantity);
        CartItem saved = cartItemRepository.save(item);
        return new CartItemDTO(saved);
    }

    @Transactional
    public void removeFromCart(User user, Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId));

        if (!item.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Access denied to this cart item");
        }

        cartItemRepository.delete(item);
    }

    @Transactional
    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }
}
