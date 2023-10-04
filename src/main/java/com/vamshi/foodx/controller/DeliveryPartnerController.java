package com.vamshi.foodx.controller;

import com.vamshi.foodx.dto.request.DeliveryPartnerRequest;
import com.vamshi.foodx.service.impl.DeliveryPartnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-partner")
public class DeliveryPartnerController {
    final DeliveryPartnerServiceImpl deliveryPartnerService;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerServiceImpl deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){

        String message = deliveryPartnerService.addPartner(deliveryPartnerRequest);
        return new ResponseEntity(message, HttpStatus.CREATED);
    }
}
