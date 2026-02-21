package com.assignment.shipping_estimator.repository;

import com.assignment.shipping_estimator.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
