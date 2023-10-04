package com.vamshi.foodx.dto.response;

import com.vamshi.foodx.dto.request.FoodItemRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartStatusResponse {

    String customerName;

    String customerAddress;

    String customerMobile;

    double cartTotal;

    List<FoodItemResponse> foodList;

    String restaurantName;
}
