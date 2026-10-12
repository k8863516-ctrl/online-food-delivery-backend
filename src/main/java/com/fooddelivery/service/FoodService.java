package com.fooddelivery.service;

import com.fooddelivery.dto.FoodItemDTO;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantService restaurantService;

    public FoodService(FoodRepository foodRepository, RestaurantService restaurantService) {
        this.foodRepository = foodRepository;
        this.restaurantService = restaurantService;
    }

    public List<FoodItemDTO> getFoodsByRestaurant(Long restaurantId) {
        return foodRepository.findByRestaurantId(restaurantId).stream()
                .map(FoodItemDTO::new)
                .collect(Collectors.toList());
    }

    public FoodItemDTO getFoodById(Long id) {
        FoodItem item = findById(id);
        return new FoodItemDTO(item);
    }

    public FoodItem findById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found with id: " + id));
    }

    public FoodItemDTO addFoodItem(Long restaurantId, FoodItemDTO dto) {
        Restaurant restaurant = restaurantService.findById(restaurantId);

        FoodItem item = new FoodItem();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategory(dto.getCategory());
        item.setImageUrl(dto.getImageUrl());
        item.setIsAvailable(dto.getIsAvailable() != null ? dto.getIsAvailable() : true);
        item.setIsVeg(dto.getIsVeg() != null ? dto.getIsVeg() : false);
        item.setRestaurant(restaurant);

        FoodItem saved = foodRepository.save(item);
        return new FoodItemDTO(saved);
    }

    public FoodItemDTO updateFoodItem(Long id, FoodItemDTO dto) {
        FoodItem item = findById(id);

        if (dto.getName() != null) item.setName(dto.getName());
        if (dto.getDescription() != null) item.setDescription(dto.getDescription());
        if (dto.getPrice() != null) item.setPrice(dto.getPrice());
        if (dto.getCategory() != null) item.setCategory(dto.getCategory());
        if (dto.getImageUrl() != null) item.setImageUrl(dto.getImageUrl());
        if (dto.getIsAvailable() != null) item.setIsAvailable(dto.getIsAvailable());
        if (dto.getIsVeg() != null) item.setIsVeg(dto.getIsVeg());

        FoodItem saved = foodRepository.save(item);
        return new FoodItemDTO(saved);
    }

    public void deleteFoodItem(Long id) {
        FoodItem item = findById(id);
        foodRepository.delete(item);
    }

    public FoodItemDTO toggleAvailability(Long id) {
        FoodItem item = findById(id);
        item.setIsAvailable(!Boolean.TRUE.equals(item.getIsAvailable()));
        FoodItem saved = foodRepository.save(item);
        return new FoodItemDTO(saved);
    }
}
