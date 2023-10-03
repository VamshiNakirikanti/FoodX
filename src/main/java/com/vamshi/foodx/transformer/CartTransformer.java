package com.vamshi.foodx.transformer;

import com.vamshi.foodx.dto.response.CartResponse;
import com.vamshi.foodx.model.Cart;

import java.util.ArrayList;

public class CartTransformer {
    public static CartResponse CartToCartResponse(Cart cart){
        return CartResponse.builder()
                .cartTotal(cart.getCartTotal())
                .foodItems(new ArrayList<>())
                .build();
    }
}
