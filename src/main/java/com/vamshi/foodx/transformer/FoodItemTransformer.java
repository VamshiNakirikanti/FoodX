package com.vamshi.foodx.transformer;

import com.vamshi.foodx.dto.response.FoodItemResponse;
import com.vamshi.foodx.model.FoodItem;

public class FoodItemTransformer {
    public static FoodItemResponse FoodToFoodResponse(FoodItem food){
        return FoodItemResponse.builder()
                .dishName(food.getMenuItem().getDishName())
                .price(food.getMenuItem().getPrice())
                .category(food.getMenuItem().getFoodCategory())
                .veg(food.getMenuItem().isVeg())
                .quantityAdded(food.getRequiredQuantity())
                .build();
    }
}
