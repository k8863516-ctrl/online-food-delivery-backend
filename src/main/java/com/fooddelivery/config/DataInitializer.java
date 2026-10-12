package com.fooddelivery.config;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RestaurantRepository restaurantRepository,
                           FoodRepository foodRepository,
                           OrderRepository orderRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Data already exists
        }

        System.out.println(">>> Initializing sample data for Online Food Delivery System...");

        // 1. Create Users
        User admin = new User(null, "System Administrator", "admin@fooddelivery.com", passwordEncoder.encode("admin123"), "+1 555-0100", "HQ 101 Tech Boulevard", "ROLE_ADMIN");
        User restaurantOwner = new User(null, "Marco Rossi", "owner@burgercraft.com", passwordEncoder.encode("owner123"), "+1 555-0101", "742 Evergreen Terrace", "ROLE_RESTAURANT");
        User customer = new User(null, "Alex Johnson", "customer@gmail.com", passwordEncoder.encode("customer123"), "+1 555-0102", "42 Wallaby Way, Suite 10", "ROLE_CUSTOMER");

        userRepository.saveAll(List.of(admin, restaurantOwner, customer));

        // 2. Create Restaurants
        Restaurant r1 = new Restaurant(null, "Burger Craft", "Artisan gourmet burgers made with 100% prime Angus beef and fresh Brioche buns.",
                "Burgers", "124 Broadway St, Downtown",
                "https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&auto=format&fit=crop&q=80",
                4.8, "20-30 min", 1.99, true, restaurantOwner);

        Restaurant r2 = new Restaurant(null, "Bella Italia", "Authentic wood-fired Neapolitan pizzas and handcrafted pasta dishes made daily.",
                "Italian", "58 Little Italy Blvd",
                "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&auto=format&fit=crop&q=80",
                4.9, "25-35 min", 2.49, true, admin);

        Restaurant r3 = new Restaurant(null, "Spice Symphony", "Traditional aromatic Indian curries, rich biryanis, and tandoori sizzlers.",
                "Indian", "204 Curry Corner Way",
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800&auto=format&fit=crop&q=80",
                4.7, "30-45 min", 2.99, true, admin);

        Restaurant r4 = new Restaurant(null, "Tokyo Sushi Bar", "Freshly sliced sashimi, signature maki rolls, and piping hot tonkotsu ramen.",
                "Japanese", "88 Sakura Avenue",
                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=800&auto=format&fit=crop&q=80",
                4.9, "35-45 min", 3.49, true, admin);

        Restaurant r5 = new Restaurant(null, "Taco Fiesta", "Vibrant Mexican street tacos, cheesy quesadillas, and slow-cooked carnitas.",
                "Mexican", "312 Sunset Strip",
                "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=800&auto=format&fit=crop&q=80",
                4.6, "20-30 min", 1.99, true, admin);

        Restaurant r6 = new Restaurant(null, "Green Bowl & Co.", "Organic nutrient-dense grain bowls, superfood salads, and cold-pressed juices.",
                "Healthy", "15 Healthy Living Way",
                "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800&auto=format&fit=crop&q=80",
                4.7, "15-25 min", 1.49, true, admin);

        restaurantRepository.saveAll(List.of(r1, r2, r3, r4, r5, r6));

        // 3. Create Food Items
        // Burger Craft foods
        FoodItem f1 = new FoodItem(null, "The Truffle Beast Burger", "Angus patty, black truffle aioli, aged cheddar, arugula on brioche", 14.99, "Burgers",
                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600&auto=format&fit=crop&q=80", true, false, r1);
        FoodItem f2 = new FoodItem(null, "Smoky Bacon & BBQ Burger", "Double beef patty, crispy smoked bacon, onion rings & house BBQ", 15.49, "Burgers",
                "https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=600&auto=format&fit=crop&q=80", true, false, r1);
        FoodItem f3 = new FoodItem(null, "Crispy Avocado Veggie Burger", "Golden quinoa patty, mashed hass avocado, vegan chipotle mayo", 12.99, "Burgers",
                "https://images.unsplash.com/photo-1520072959219-c595dc870360?w=600&auto=format&fit=crop&q=80", true, true, r1);
        FoodItem f4 = new FoodItem(null, "Truffle Parmesan Fries", "Hand-cut Idaho potatoes tossed in white truffle oil and grated parmigiano", 6.99, "Sides",
                "https://images.unsplash.com/photo-1576107232684-1279f3908594?w=600&auto=format&fit=crop&q=80", true, true, r1);

        // Bella Italia foods
        FoodItem f5 = new FoodItem(null, "Margherita Napoletana", "San Marzano tomatoes, fresh buffalo mozzarella, fragrant basil leaves", 16.99, "Pizza",
                "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=600&auto=format&fit=crop&q=80", true, true, r2);
        FoodItem f6 = new FoodItem(null, "Spicy Diavola Pizza", "Spicy Calabrian salami, red chili flakes, crushed garlic & mozzarella", 18.99, "Pizza",
                "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?w=600&auto=format&fit=crop&q=80", true, false, r2);
        FoodItem f7 = new FoodItem(null, "Creamy Fettuccine Alfredo", "Fresh egg ribbons in silky garlic cream, butter, and Parmigiano-Reggiano", 17.50, "Pasta",
                "https://images.unsplash.com/photo-1645112411341-6c4fd023714a?w=600&auto=format&fit=crop&q=80", true, true, r2);

        // Spice Symphony foods
        FoodItem f8 = new FoodItem(null, "Royal Hyderabadi Dum Biryani", "Slow-cooked fragrant basmati rice with tender spiced chicken & saffron", 15.99, "Biryani",
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&auto=format&fit=crop&q=80", true, false, r3);
        FoodItem f9 = new FoodItem(null, "Butter Chicken & Garlic Naan", "Char-grilled chicken cubes simmered in velvety tomato-butter gravy", 16.49, "Curry",
                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=600&auto=format&fit=crop&q=80", true, false, r3);
        FoodItem f10 = new FoodItem(null, "Paneer Tikka Masala", "Cottage cheese cubes marinated in tandoori spices with bell peppers", 14.99, "Curry",
                "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=600&auto=format&fit=crop&q=80", true, true, r3);

        // Tokyo Sushi Bar foods
        FoodItem f11 = new FoodItem(null, "Dragon Maki Roll", "Eel, crispy tempura shrimp, avocado, tobiko and sweet unagi glaze", 16.99, "Sushi",
                "https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=600&auto=format&fit=crop&q=80", true, false, r4);
        FoodItem f12 = new FoodItem(null, "Spicy Salmon Crunch Roll", "Fresh Atlantic salmon, chili mayo, tempura flakes and scallions", 14.50, "Sushi",
                "https://images.unsplash.com/photo-1553621042-f6e147245754?w=600&auto=format&fit=crop&q=80", true, false, r4);
        FoodItem f13 = new FoodItem(null, "Rich Tonkotsu Chashu Ramen", "18-hour simmered pork broth, soft nitamago egg, menma bamboo & nori", 15.50, "Ramen",
                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600&auto=format&fit=crop&q=80", true, false, r4);

        // Taco Fiesta foods
        FoodItem f14 = new FoodItem(null, "Slow-Cooked Carnitas Tacos (3 pcs)", "Tender pork, pickled red onion, cilantro & salsa verde on corn tortillas", 11.99, "Tacos",
                "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=600&auto=format&fit=crop&q=80", true, false, r5);
        FoodItem f15 = new FoodItem(null, "Grilled Carne Asada Burrito", "Marinated flank steak, black beans, Mexican rice, guacamole & pico", 13.49, "Burritos",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600&auto=format&fit=crop&q=80", true, false, r5);

        // Green Bowl foods
        FoodItem f16 = new FoodItem(null, "Mediterranean Quinoa Bowl", "Kalamata olives, Persian cucumber, feta, lemon tahini vinaigrette", 12.50, "Bowls",
                "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=600&auto=format&fit=crop&q=80", true, true, r6);
        FoodItem f17 = new FoodItem(null, "Berry Glow Superfood Smoothie", "Acai, wild blueberries, almond milk, organic chia seeds & agave", 7.99, "Beverages",
                "https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=600&auto=format&fit=crop&q=80", true, true, r6);

        foodRepository.saveAll(List.of(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17));

        // 4. Create an Initial Sample Order
        CustomerOrder sampleOrder = new CustomerOrder();
        sampleOrder.setOrderNumber("ORD-984210");
        sampleOrder.setUser(customer);
        sampleOrder.setRestaurant(r1);
        sampleOrder.setTotalAmount(23.97);
        sampleOrder.setStatus("DELIVERED");
        sampleOrder.setDeliveryAddress("42 Wallaby Way, Suite 10");
        sampleOrder.setPaymentMethod("CARD");
        sampleOrder.setPaymentStatus("PAID");

        OrderItem item1 = new OrderItem(null, sampleOrder, f1, f1.getName(), 1, f1.getPrice());
        OrderItem item2 = new OrderItem(null, sampleOrder, f4, f4.getName(), 1, f4.getPrice());
        sampleOrder.getItems().add(item1);
        sampleOrder.getItems().add(item2);

        orderRepository.save(sampleOrder);

        System.out.println(">>> Sample data initialized successfully!");
    }
}
