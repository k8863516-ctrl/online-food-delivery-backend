package com.fooddelivery.service;

import com.fooddelivery.dto.CreateOrderRequest;
import com.fooddelivery.dto.DashboardStatsDTO;
import com.fooddelivery.dto.OrderResponseDTO;
import com.fooddelivery.entity.*;
import com.fooddelivery.exception.BadRequestException;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.CartItemRepository;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        CartItemRepository cartItemRepository,
                        RestaurantService restaurantService,
                        RestaurantRepository restaurantRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(User user, CreateOrderRequest req) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Your cart is empty! Cannot create an order.");
        }

        Restaurant restaurant = restaurantService.findById(req.getRestaurantId());

        CustomerOrder order = new CustomerOrder();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(req.getDeliveryAddress());
        order.setPaymentMethod(req.getPaymentMethod() != null ? req.getPaymentMethod() : "COD");
        order.setPaymentStatus("PENDING");
        order.setStatus("CONFIRMED");

        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(item.getFoodItem());
            orderItem.setFoodName(item.getFoodItem().getName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtOrder(item.getFoodItem().getPrice());
            order.getItems().add(orderItem);

            subtotal += item.getQuantity() * item.getFoodItem().getPrice();
        }

        double deliveryFee = restaurant.getDeliveryFee() != null ? restaurant.getDeliveryFee() : 2.99;
        order.setTotalAmount(Math.round((subtotal + deliveryFee) * 100.0) / 100.0);

        CustomerOrder savedOrder = orderRepository.save(order);

        // Clear user's cart
        cartItemRepository.deleteByUser(user);

        return new OrderResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getRestaurantOrders(Long restaurantId) {
        return orderRepository.findByRestaurantIdOrderByCreatedAtDesc(restaurantId).stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long id) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return new OrderResponseDTO(order);
    }

    public OrderResponseDTO updateOrderStatus(Long orderId, String status, String paymentStatus) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (status != null && !status.isBlank()) {
            order.setStatus(status.toUpperCase().trim());
        }
        if (paymentStatus != null && !paymentStatus.isBlank()) {
            order.setPaymentStatus(paymentStatus.toUpperCase().trim());
        }

        CustomerOrder updated = orderRepository.save(order);
        return new OrderResponseDTO(updated);
    }

    public OrderResponseDTO cancelOrder(User user, Long orderId) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().equals("ROLE_ADMIN")) {
            throw new BadRequestException("You can only cancel your own orders");
        }

        if ("DELIVERED".equalsIgnoreCase(order.getStatus())) {
            throw new BadRequestException("Delivered orders cannot be cancelled");
        }

        order.setStatus("CANCELLED");
        CustomerOrder updated = orderRepository.save(order);
        return new OrderResponseDTO(updated);
    }

    public DashboardStatsDTO getDashboardStats() {
        List<CustomerOrder> allOrders = orderRepository.findAll();
        long totalOrders = allOrders.size();
        double totalRevenue = orderRepository.calculateTotalRevenue();
        long totalUsers = userRepository.count();
        long totalRestaurants = restaurantRepository.count();

        long deliveredOrders = allOrders.stream()
                .filter(o -> "DELIVERED".equalsIgnoreCase(o.getStatus()))
                .count();

        long pendingOrders = allOrders.stream()
                .filter(o -> "PENDING".equalsIgnoreCase(o.getStatus()) || "CONFIRMED".equalsIgnoreCase(o.getStatus()) || "PREPARING".equalsIgnoreCase(o.getStatus()))
                .count();

        return new DashboardStatsDTO(totalOrders, totalRevenue, totalUsers, totalRestaurants, deliveredOrders, pendingOrders);
    }
}
