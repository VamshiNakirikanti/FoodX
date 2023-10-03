package com.vamshi.foodx.dto.request;

import com.vamshi.foodx.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    String name;

    String email;

    String address;

    String mobileNo;

    Gender gender;
}
