package com.vamshi.foodx.repository;

import com.vamshi.foodx.dto.response.CartStatusResponse;
import com.vamshi.foodx.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
}
