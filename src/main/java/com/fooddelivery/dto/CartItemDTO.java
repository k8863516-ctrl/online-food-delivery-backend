package com.fooddelivery.dto;

import com.fooddelivery.entity.CartItem;

public class CartItemDTO {

    private Long id;
    private Long foodItemId;
    private String foodName;
    private Double price;
    private String imageUrl;
    private Integer quantity;
    private Double itemTotal;
    private Long restaurantId;
    private String restaurantName;

    public CartItemDTO() {
    }

    public CartItemDTO(CartItem item) {
        if (item != null) {
            this.id = item.getId();
            this.quantity = item.getQuantity();
            if (item.getFoodItem() != null) {
                this.foodItemId = item.getFoodItem().getId();
                this.foodName = item.getFoodItem().getName();
                this.price = item.getFoodItem().getPrice();
                this.imageUrl = item.getFoodItem().getImageUrl();
                this.itemTotal = item.getQuantity() * item.getFoodItem().getPrice();
                if (item.getFoodItem().getRestaurant() != null) {
                    this.restaurantId = item.getFoodItem().getRestaurant().getId();
                    this.restaurantName = item.getFoodItem().getRestaurant().getName();
                }
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Long foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Double itemTotal) {
        this.itemTotal = itemTotal;
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
