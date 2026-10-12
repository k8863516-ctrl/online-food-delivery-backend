package com.fooddelivery.dto;

public class DashboardStatsDTO {

    private long totalOrders;
    private double totalRevenue;
    private long totalUsers;
    private long totalRestaurants;
    private long deliveredOrders;
    private long pendingOrders;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(long totalOrders, double totalRevenue, long totalUsers, long totalRestaurants, long deliveredOrders, long pendingOrders) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.totalUsers = totalUsers;
        this.totalRestaurants = totalRestaurants;
        this.deliveredOrders = deliveredOrders;
        this.pendingOrders = pendingOrders;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalRestaurants() {
        return totalRestaurants;
    }

    public void setTotalRestaurants(long totalRestaurants) {
        this.totalRestaurants = totalRestaurants;
    }

    public long getDeliveredOrders() {
        return deliveredOrders;
    }

    public void setDeliveredOrders(long deliveredOrders) {
        this.deliveredOrders = deliveredOrders;
    }

    public long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }
}
