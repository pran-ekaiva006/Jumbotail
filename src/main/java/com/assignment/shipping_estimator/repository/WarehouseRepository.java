package com.assignment.shipping_estimator.repository;
import com.assignment.shipping_estimator.model.Warehouse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
