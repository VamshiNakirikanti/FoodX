package com.vamshi.foodx.transformer;

import com.vamshi.foodx.dto.request.MenuItemRequest;
import com.vamshi.foodx.dto.response.MenuItemResponse;
import com.vamshi.foodx.model.MenuItem;

public class MenuItemTransformer {
    public static MenuItem MenuRequestToMenuItem(MenuItemRequest menuItemRequest){
        return MenuItem.builder()
                .dishName(menuItemRequest.getDishName())
                .price(menuItemRequest.getPrice())
                .foodCategory(menuItemRequest.getCategory())
                .veg(menuItemRequest.isVeg())
                .available(menuItemRequest.isAvailable())
                .build();
    }

    public static MenuItemResponse MenuItemToMenuResponse(MenuItem menuItem){
        return MenuItemResponse.builder()
                .dishName(menuItem.getDishName())
                .price(menuItem.getPrice())
                .veg(menuItem.isVeg())
                .category(menuItem.getFoodCategory())
                .build();
    }
}
