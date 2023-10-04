package com.vamshi.foodx.controller;

import com.vamshi.foodx.dto.request.FoodItemRequest;
import com.vamshi.foodx.dto.response.CartStatusResponse;
import com.vamshi.foodx.service.CartService;
import com.vamshi.foodx.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity addFoodItemToCart(@RequestBody FoodItemRequest foodItemRequest){
        try {
            CartStatusResponse cartStatusResponse = cartService.addFoodItemToCart(foodItemRequest);
            return new ResponseEntity(cartStatusResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
