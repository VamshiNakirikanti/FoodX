package com.vamshi.foodx.service.impl;

import com.vamshi.foodx.dto.request.FoodItemRequest;
import com.vamshi.foodx.dto.response.CartStatusResponse;
import com.vamshi.foodx.dto.response.FoodItemResponse;
import com.vamshi.foodx.exception.CustomerNotFoundException;
import com.vamshi.foodx.exception.MenuItemNotFoundException;
import com.vamshi.foodx.model.*;
import com.vamshi.foodx.repository.CartRepository;
import com.vamshi.foodx.repository.CustomerRepository;
import com.vamshi.foodx.repository.FoodItemRepository;
import com.vamshi.foodx.repository.MenuItemRepository;
import com.vamshi.foodx.service.CartService;
import com.vamshi.foodx.transformer.FoodItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CartServiceImpl implements CartService {
    final CustomerRepository customerRepository;
    final MenuItemRepository menuItemRepository;
    final CartRepository cartRepository;
    final FoodItemRepository foodItemRepository;

    @Autowired
    public CartServiceImpl(CustomerRepository customerRepository,
                           MenuItemRepository menuItemRepository,
                           CartRepository cartRepository,
                           FoodItemRepository foodItemRepository) {
        this.customerRepository = customerRepository;
        this.menuItemRepository = menuItemRepository;
        this.cartRepository = cartRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public CartStatusResponse addFoodItemToCart(FoodItemRequest foodItemRequest) {

        Customer customer = customerRepository.findByMobileNo(foodItemRequest.getCustomerMobile());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exisit");
        }

        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(foodItemRequest.getMenuItemId());
        if(menuItemOptional.isEmpty()){
            throw new MenuItemNotFoundException("Item not available in the restaurant!!!");
        }

        MenuItem menuItem = menuItemOptional.get();
        if(!menuItem.getRestaurant().isOpened() || !menuItem.isAvailable()) {
            throw new MenuItemNotFoundException("Given dish is out of stock for now!!!");
        }

        Cart cart = customer.getCart();

        // having item from same restaurant
        if(cart.getFoodItems().size()!=0){
            Restaurant currRestaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
            Restaurant newRestaurant = menuItem.getRestaurant();

            if(!currRestaurant.equals(newRestaurant)){
                List<FoodItem> foodItems = cart.getFoodItems();
                for(FoodItem foodItem: foodItems) {
                    foodItem.setCart(null);
                    foodItem.setOrder(null);
                    foodItem.setMenuItem(null);
                }
                cart.setCartTotal(0);
                cart.getFoodItems().clear();
                foodItemRepository.deleteAll(foodItems);
            }
        }

        boolean alreadyExists = false;
        FoodItem savedFoodItem = null;
        if(cart.getFoodItems().size()!=0){
            for(FoodItem foodItem: cart.getFoodItems()){
                if(foodItem.getMenuItem().getId()==menuItem.getId()){
                    savedFoodItem = foodItem;
                    int curr = foodItem.getRequiredQuantity();
                    foodItem.setRequiredQuantity(curr+foodItemRequest.getRequiredQuantity());
                    alreadyExists = true;
                    break;
                }
            }
        }

        if(!alreadyExists){
            FoodItem foodItem = FoodItem.builder()
                    .menuItem(menuItem)
                    .requiredQuantity(foodItemRequest.getRequiredQuantity())
                    .totalCost(foodItemRequest.getRequiredQuantity()*menuItem.getPrice())
                    .build();

            savedFoodItem = foodItemRepository.save(foodItem);
            cart.getFoodItems().add(savedFoodItem);
            menuItem.getFoodItems().add(savedFoodItem);
            savedFoodItem.setCart(cart);
        }

        double cartTotal = 0;
        for(FoodItem food: cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }

        cart.setCartTotal(cartTotal);
        // save
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        // prepare
        List<FoodItemResponse> foodResponseList = new ArrayList<>();
        for(FoodItem food: cart.getFoodItems()){
            foodResponseList.add(FoodItemTransformer.FoodToFoodResponse(food));
        }

        return CartStatusResponse.builder()
                .customerName(savedCart.getCustomer().getName())
                .customerMobile(savedCart.getCustomer().getMobileNo())
                .customerAddress(savedCart.getCustomer().getAddress())
                .foodList(foodResponseList)
                .restaurantName(savedMenuItem.getRestaurant().getName())
                .cartTotal(cartTotal)
                .build();

    }
}
