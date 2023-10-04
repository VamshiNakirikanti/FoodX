package com.vamshi.foodx.service.impl;

import com.vamshi.foodx.dto.response.OrderResponse;
import com.vamshi.foodx.exception.CustomerNotFoundException;
import com.vamshi.foodx.exception.EmptyCartException;
import com.vamshi.foodx.model.*;
import com.vamshi.foodx.repository.CustomerRepository;
import com.vamshi.foodx.repository.DeliverPartnerRepository;
import com.vamshi.foodx.repository.OrderEntityRepository;
import com.vamshi.foodx.repository.RestaurantRepository;
import com.vamshi.foodx.service.OrderService;
import com.vamshi.foodx.transformer.OrderTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {

    final CustomerRepository customerRepository;
    final OrderEntityRepository orderEntityRepository;
    final DeliverPartnerRepository deliverPartnerRepository;
    final RestaurantRepository restaurantRepository;

    public OrderServiceImpl(CustomerRepository customerRepository, OrderEntityRepository orderEntityRepository, DeliverPartnerRepository deliverPartnerRepository, RestaurantRepository restaurantRepository) {
        this.customerRepository = customerRepository;
        this.orderEntityRepository = orderEntityRepository;
        this.deliverPartnerRepository = deliverPartnerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public OrderResponse placeOrder(String customerMobile) {

        // verify the customer
        Customer customer = customerRepository.findByMobileNo(customerMobile);
        if(customer == null){
            throw new CustomerNotFoundException("Invalid mobile number!!!");
        }

        // verify if cart is empty or not
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()==0){
            throw new EmptyCartException("Sorry! Your cart is empty!!!");
        }

        // find a delivery partner to deliver. Randomly
        DeliveryPartner partner = deliverPartnerRepository.findRandomDeliveryPartner();
        Restaurant restaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();

        // prepare the order entity
        OrderEntity order = OrderTransformer.prepareOrderEntity(cart);

        OrderEntity savedOrder = orderEntityRepository.save(order);

        order.setCustomer(customer);
        order.setDeliveryPartner(partner);
        order.setRestaurant(restaurant);
        order.setFoodItems(cart.getFoodItems());

        customer.getOrders().add(savedOrder);
        partner.getOrders().add(savedOrder);
        restaurant.getOrders().add(savedOrder);

        for(FoodItem foodItem: cart.getFoodItems()){
            foodItem.setCart(null);
            foodItem.setOrder(savedOrder);
        }
        clearCart(cart);

        customerRepository.save(customer);
        deliverPartnerRepository.save(partner);
        restaurantRepository.save(restaurant);

        // prepare orderresponse
        return OrderTransformer.OrderToOrderResponse(savedOrder);
    }

    private void clearCart(Cart cart) {
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
