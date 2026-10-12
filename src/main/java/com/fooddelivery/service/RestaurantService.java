package com.fooddelivery.service;

import com.fooddelivery.dto.RestaurantDTO;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.entity.User;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDTO> getAllRestaurants(String cuisine, String search) {
        List<Restaurant> list;
        if (search != null && !search.isBlank()) {
            list = restaurantRepository.searchRestaurants(search.trim());
        } else if (cuisine != null && !cuisine.isBlank() && !cuisine.equalsIgnoreCase("all")) {
            list = restaurantRepository.findByCuisineIgnoreCase(cuisine.trim());
        } else {
            list = restaurantRepository.findAll();
        }

        return list.stream()
                .map(RestaurantDTO::new)
                .collect(Collectors.toList());
    }

    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = findById(id);
        return new RestaurantDTO(restaurant);
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    public RestaurantDTO createRestaurant(RestaurantDTO dto, User owner) {
        Restaurant r = new Restaurant();
        r.setName(dto.getName());
        r.setDescription(dto.getDescription());
        r.setCuisine(dto.getCuisine());
        r.setAddress(dto.getAddress());
        r.setImageUrl(dto.getImageUrl());
        if (dto.getRating() != null) r.setRating(dto.getRating());
        if (dto.getDeliveryTime() != null) r.setDeliveryTime(dto.getDeliveryTime());
        if (dto.getDeliveryFee() != null) r.setDeliveryFee(dto.getDeliveryFee());
        if (dto.getIsOpen() != null) r.setIsOpen(dto.getIsOpen());
        r.setOwner(owner);

        Restaurant saved = restaurantRepository.save(r);
        return new RestaurantDTO(saved);
    }

    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO dto) {
        Restaurant r = findById(id);
        if (dto.getName() != null) r.setName(dto.getName());
        if (dto.getDescription() != null) r.setDescription(dto.getDescription());
        if (dto.getCuisine() != null) r.setCuisine(dto.getCuisine());
        if (dto.getAddress() != null) r.setAddress(dto.getAddress());
        if (dto.getImageUrl() != null) r.setImageUrl(dto.getImageUrl());
        if (dto.getRating() != null) r.setRating(dto.getRating());
        if (dto.getDeliveryTime() != null) r.setDeliveryTime(dto.getDeliveryTime());
        if (dto.getDeliveryFee() != null) r.setDeliveryFee(dto.getDeliveryFee());
        if (dto.getIsOpen() != null) r.setIsOpen(dto.getIsOpen());

        Restaurant updated = restaurantRepository.save(r);
        return new RestaurantDTO(updated);
    }

    public void deleteRestaurant(Long id) {
        Restaurant r = findById(id);
        restaurantRepository.delete(r);
    }

    public RestaurantDTO getRestaurantByOwner(User owner) {
        Restaurant r = restaurantRepository.findByOwner(owner)
                .orElseThrow(() -> new ResourceNotFoundException("No restaurant registered for user: " + owner.getEmail()));
        return new RestaurantDTO(r);
    }
}
