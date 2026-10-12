package com.fooddelivery.dto;

import com.fooddelivery.entity.FoodItem;

public class FoodItemDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private Boolean isAvailable;
    private Boolean isVeg;
    private Long restaurantId;
    private String restaurantName;

    public FoodItemDTO() {
    }

    public FoodItemDTO(FoodItem item) {
        if (item != null) {
            this.id = item.getId();
            this.name = item.getName();
            this.description = item.getDescription();
            this.price = item.getPrice();
            this.category = item.getCategory();
            this.imageUrl = item.getImageUrl();
            this.isAvailable = item.getIsAvailable();
            this.isVeg = item.getIsVeg();
            if (item.getRestaurant() != null) {
                this.restaurantId = item.getRestaurant().getId();
                this.restaurantName = item.getRestaurant().getName();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public Boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean veg) {
        isVeg = veg;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
