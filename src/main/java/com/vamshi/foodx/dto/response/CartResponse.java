package com.vamshi.foodx.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    double cartTotal;

    List<FoodItemResponse> foodItems;
}
