package com.fooddelivery.dto;

import com.fooddelivery.entity.OrderItem;

public class OrderItemDTO {

    private Long id;
    private Long foodItemId;
    private String foodName;
    private Integer quantity;
    private Double priceAtOrder;
    private Double itemTotal;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderItem item) {
        if (item != null) {
            this.id = item.getId();
            this.foodName = item.getFoodName();
            this.quantity = item.getQuantity();
            this.priceAtOrder = item.getPriceAtOrder();
            this.itemTotal = item.getQuantity() * item.getPriceAtOrder();
            if (item.getFoodItem() != null) {
                this.foodItemId = item.getFoodItem().getId();
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(Double priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public Double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Double itemTotal) {
        this.itemTotal = itemTotal;
    }
}
