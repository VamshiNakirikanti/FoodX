package com.vamshi.foodx.service.impl;

import com.vamshi.foodx.dto.request.DeliveryPartnerRequest;
import com.vamshi.foodx.model.DeliveryPartner;
import com.vamshi.foodx.repository.DeliverPartnerRepository;
import com.vamshi.foodx.service.DeliveryPartnerService;
import com.vamshi.foodx.transformer.DeliveryPartnerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {
    final DeliverPartnerRepository deliverPartnerRepository;

    @Autowired
    public DeliveryPartnerServiceImpl(DeliverPartnerRepository deliverPartnerRepository) {
        this.deliverPartnerRepository = deliverPartnerRepository;
    }
    @Override
    public String addPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        //dto -> entity
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.PartnerRequestToDeliveryPartner(deliveryPartnerRequest);

        // save
        DeliveryPartner savedPartner = deliverPartnerRepository.save(deliveryPartner);

        return "You have been successfully regsitered!!!";
    }
}
