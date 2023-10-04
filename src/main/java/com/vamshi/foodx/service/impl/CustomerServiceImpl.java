package com.vamshi.foodx.service.impl;

import com.vamshi.foodx.dto.request.CustomerRequest;
import com.vamshi.foodx.dto.response.CustomerResponse;
import com.vamshi.foodx.exception.CustomerNotFoundException;
import com.vamshi.foodx.model.Cart;
import com.vamshi.foodx.model.Customer;
import com.vamshi.foodx.repository.CustomerRepository;
import com.vamshi.foodx.service.CustomerService;
import com.vamshi.foodx.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        Customer customer = CustomerTransformer.CustomerRequestToCustomer(customerRequest);

        Cart cart = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();
        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
    }

    @Override
    public CustomerResponse findCustomerByMobile(String mobile) {
        Customer customer = customerRepository.findByMobileNo(mobile);
        if(customer==null){
            throw new CustomerNotFoundException("Invalid mobile no!!!");
        }
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }
}
