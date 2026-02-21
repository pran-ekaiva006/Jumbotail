package com.assignment.shipping_estimator.repository;

import com.assignment.shipping_estimator.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

