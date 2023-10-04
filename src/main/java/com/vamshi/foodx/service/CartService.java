package com.vamshi.foodx.service;

import com.vamshi.foodx.dto.request.FoodItemRequest;
import com.vamshi.foodx.dto.response.CartStatusResponse;

public interface CartService {
    public CartStatusResponse addFoodItemToCart(FoodItemRequest foodItemRequest);
}
