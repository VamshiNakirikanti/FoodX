package com.vamshi.foodx.service;

import com.vamshi.foodx.dto.request.MenuItemRequest;
import com.vamshi.foodx.dto.request.RestaurantRequest;
import com.vamshi.foodx.dto.response.RestaurantResponse;

public interface RestaurantService {
    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest);

    public String changeOpenedStatus(int id);

    public RestaurantResponse addMenuItemtToRestaurant(MenuItemRequest menuItemRequest);
}
