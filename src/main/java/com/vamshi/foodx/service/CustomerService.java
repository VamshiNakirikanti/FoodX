package com.vamshi.foodx.service;


import com.vamshi.foodx.dto.request.CustomerRequest;
import com.vamshi.foodx.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest customerRequest);
}
