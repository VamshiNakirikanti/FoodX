package com.vamshi.foodx.service.impl;

import com.vamshi.foodx.dto.request.MenuItemRequest;
import com.vamshi.foodx.dto.request.RestaurantRequest;
import com.vamshi.foodx.dto.response.RestaurantResponse;
import com.vamshi.foodx.exception.RestaurantNotFoundException;
import com.vamshi.foodx.model.MenuItem;
import com.vamshi.foodx.model.Restaurant;
import com.vamshi.foodx.repository.RestaurantRepository;
import com.vamshi.foodx.service.RestaurantService;
import com.vamshi.foodx.transformer.MenuItemTransformer;
import com.vamshi.foodx.transformer.RestaurantTransformer;
import com.vamshi.foodx.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    final RestaurantRepository restaurantRespository;
    final ValidationUtils validationUtils;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRespository, ValidationUtils validationUtils) {
        this.restaurantRespository = restaurantRespository;
        this.validationUtils = validationUtils;
    }


    @Override
    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) {
        // dto -> model
        Restaurant restaurant = RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        //persist/save the model in db
        Restaurant savedRestaurant = restaurantRespository.save(restaurant);
        // prepare response dto from model
        return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
    }

    @Override
    public String changeOpenedStatus(int id) {
        //check whether id is valid or not
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }

        Restaurant restaurant = restaurantRespository.findById(id).get();
        restaurant.setOpened(!restaurant.isOpened());
        restaurantRespository.save(restaurant);

        if(restaurant.isOpened()){
            return "Restaurant is opened now!!!";
        }

        return "Restaurant is closed";
    }

    @Override
    public RestaurantResponse addMenuItemtToRestaurant(MenuItemRequest menuItemRequest) {
            // check restaurant is valid or not
            if(!validationUtils.validateRestaurantId(menuItemRequest.getRestaurantId())){
                throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
            }

            Restaurant restaurant = restaurantRespository.findById(menuItemRequest.getRestaurantId()).get();
            // make food entity
            MenuItem menuItem = MenuItemTransformer.MenuRequestToMenuItem(menuItemRequest);
            menuItem.setRestaurant(restaurant);

            restaurant.getMenuItems().add(menuItem);

            // save rest and food
            Restaurant savedRestaurant = restaurantRespository.save(restaurant);

            // prepare response
            return RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant);
        }

}
