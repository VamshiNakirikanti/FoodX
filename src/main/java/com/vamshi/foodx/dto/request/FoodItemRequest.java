package com.vamshi.foodx.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemRequest {

    int requiredQuantity;

    String customerMobile;

    int menuItemId;

}
