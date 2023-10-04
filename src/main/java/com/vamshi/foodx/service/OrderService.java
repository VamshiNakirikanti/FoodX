package com.vamshi.foodx.service;

import com.vamshi.foodx.dto.response.OrderResponse;

public interface OrderService {
   public OrderResponse placeOrder(String customerMobile);
}
