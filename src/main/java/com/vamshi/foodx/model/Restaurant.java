package com.vamshi.foodx.model;

import com.vamshi.foodx.Enum.RestaurantCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    @Enumerated(EnumType.STRING)
    RestaurantCategory restaurantCategory;

    boolean opened;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<OrderEntity> orders = new ArrayList<>();
}
