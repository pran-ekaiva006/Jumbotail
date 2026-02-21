package com.assignment.shipping_estimator.service;

import com.assignment.shipping_estimator.dto.*;
import com.assignment.shipping_estimator.exception.InvalidRequestException;
import com.assignment.shipping_estimator.exception.ResourceNotFoundException;
import com.assignment.shipping_estimator.model.Customer;
import com.assignment.shipping_estimator.model.Warehouse;
import com.assignment.shipping_estimator.repository.CustomerRepository;
import com.assignment.shipping_estimator.strategy.TransportStrategy;
import com.assignment.shipping_estimator.strategy.TransportStrategyFactory;
import com.assignment.shipping_estimator.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private static final double BASE_CHARGE = 10.0;
    private static final double EXPRESS_SURCHARGE_PER_KG = 1.2;
    private static final double DEFAULT_WEIGHT_KG = 5.0;

    private final WarehouseService warehouseService;
    private final CustomerRepository customerRepository;
    private final TransportStrategyFactory transportStrategyFactory;

    @Cacheable(value = "shippingCharge", key = "#warehouseId + '-' + #customerId + '-' + #deliverySpeed")
    public Double calculateShippingCharge(Long warehouseId, Long customerId, String deliverySpeed) {
        log.debug("Calculating shipping charge for warehouse: {}, customer: {}, speed: {}", 
                warehouseId, customerId, deliverySpeed);

        validateDeliverySpeed(deliverySpeed);

        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        double distance = DistanceUtil.calculateDistance(
                warehouse.getLatitude(), warehouse.getLongitude(),
                customer.getLatitude(), customer.getLongitude()
        );

        return calculateCharge(distance, DEFAULT_WEIGHT_KG, deliverySpeed);
    }

    public ShippingResponse calculateFullShipping(ShippingRequest request) {
        log.debug("Calculating full shipping for request: {}", request);

        validateDeliverySpeed(request.getDeliverySpeed());

        NearestWarehouseResponse nearestWarehouse = warehouseService.findNearestWarehouse(request.getSellerId());
        
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", request.getCustomerId()));

        double distance = DistanceUtil.calculateDistance(
                nearestWarehouse.getWarehouseLocation().getLat(),
                nearestWarehouse.getWarehouseLocation().getLng(),
                customer.getLatitude(),
                customer.getLongitude()
        );

        TransportStrategy strategy = transportStrategyFactory.getStrategy(distance);
        double shippingCharge = calculateCharge(distance, DEFAULT_WEIGHT_KG, request.getDeliverySpeed());

        return ShippingResponse.builder()
                .shippingCharge(Math.round(shippingCharge * 100.0) / 100.0)
                .nearestWarehouse(nearestWarehouse)
                .transportMode(strategy.getModeName())
                .distance(Math.round(distance * 100.0) / 100.0)
                .deliverySpeed(request.getDeliverySpeed().toUpperCase())
                .build();
    }

    private double calculateCharge(double distance, double weightKg, String deliverySpeed) {
        TransportStrategy strategy = transportStrategyFactory.getStrategy(distance);
        
        double charge = BASE_CHARGE + (distance * weightKg * strategy.getRatePerKmPerKg());

        if ("express".equalsIgnoreCase(deliverySpeed)) {
            charge += (weightKg * EXPRESS_SURCHARGE_PER_KG);
        }

        return charge;
    }

    private void validateDeliverySpeed(String deliverySpeed) {
        if (deliverySpeed == null || 
            (!deliverySpeed.equalsIgnoreCase("standard") && !deliverySpeed.equalsIgnoreCase("express"))) {
            throw new InvalidRequestException("Delivery speed must be 'standard' or 'express'");
        }
    }
}
