package com.vamshi.foodx.repository;

import com.vamshi.foodx.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverPartnerRepository extends JpaRepository<DeliveryPartner,Integer> {
    String findRandomPartner = "select p from DeliveryPartner p order by RAND() LIMIT 1";

    @Query(value = findRandomPartner)
    DeliveryPartner findRandomDeliveryPartner();
}
