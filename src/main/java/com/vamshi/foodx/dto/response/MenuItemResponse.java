package com.vamshi.foodx.dto.response;

import com.vamshi.foodx.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {

    String dishName;

    double price;

    FoodCategory category;

    boolean veg;
}
