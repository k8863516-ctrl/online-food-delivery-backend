package com.fooddelivery.dto;

import com.fooddelivery.entity.Restaurant;

public class RestaurantDTO {

    private Long id;
    private String name;
    private String description;
    private String cuisine;
    private String address;
    private String imageUrl;
    private Double rating;
    private String deliveryTime;
    private Double deliveryFee;
    private Boolean isOpen;
    private Long ownerId;
    private String ownerName;

    public RestaurantDTO() {
    }

    public RestaurantDTO(Restaurant r) {
        if (r != null) {
            this.id = r.getId();
            this.name = r.getName();
            this.description = r.getDescription();
            this.cuisine = r.getCuisine();
            this.address = r.getAddress();
            this.imageUrl = r.getImageUrl();
            this.rating = r.getRating();
            this.deliveryTime = r.getDeliveryTime();
            this.deliveryFee = r.getDeliveryFee();
            this.isOpen = r.getIsOpen();
            if (r.getOwner() != null) {
                this.ownerId = r.getOwner().getId();
                this.ownerName = r.getOwner().getName();
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

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean open) {
        isOpen = open;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
