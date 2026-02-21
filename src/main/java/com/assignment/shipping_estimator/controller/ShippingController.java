package com.assignment.shipping_estimator.controller;

import com.assignment.shipping_estimator.dto.ShippingResponse;
import com.assignment.shipping_estimator.model.Customer;
import com.assignment.shipping_estimator.model.Seller;
import com.assignment.shipping_estimator.model.Warehouse;
import com.assignment.shipping_estimator.repository.CustomerRepository;
import com.assignment.shipping_estimator.repository.SellerRepository;
import com.assignment.shipping_estimator.repository.WarehouseRepository;
import com.assignment.shipping_estimator.util.DistanceUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {

    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;

    public ShippingController(WarehouseRepository warehouseRepository,
                              CustomerRepository customerRepository,
                              SellerRepository sellerRepository) {
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
    }

    @GetMapping("/shipping-charge")
    public double getShippingCharge(@RequestParam Long warehouseId,
                                    @RequestParam Long customerId,
                                    @RequestParam String deliverySpeed) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (warehouse == null || customer == null) {
            return 0;
        }

        double distance = DistanceUtil.calculateDistance(
                warehouse.getLat(),
                warehouse.getLng(),
                customer.getLat(),
                customer.getLng()
        );

        double rate;
        if (distance <= 100) {
            rate = 3;
        } else if (distance <= 500) {
            rate = 2;
        } else {
            rate = 1;
        }

        double weight = 10;

        double shippingCharge = distance * rate * weight;
        shippingCharge += 10;

        if (deliverySpeed.equalsIgnoreCase("express")) {
            shippingCharge += (1.2 * weight);
        }

        return shippingCharge;
    }

    @GetMapping("/shipping-charge/calculate")
    public ShippingResponse calculateShipping(@RequestParam Long sellerId,
                                              @RequestParam Long customerId,
                                              @RequestParam String deliverySpeed) {

        Seller seller = sellerRepository.findById(sellerId).orElse(null);

        if (seller == null) {
            return new ShippingResponse(0, null);
        }

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouseRepository.findAll()) {

            double distance = DistanceUtil.calculateDistance(
                    seller.getLat(),
                    seller.getLng(),
                    warehouse.getLat(),
                    warehouse.getLng()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearest = warehouse;
            }
        }

        if (nearest == null) {
            return new ShippingResponse(0, null);
        }

        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            return new ShippingResponse(0, nearest.getId());
        }

        double distance = DistanceUtil.calculateDistance(
                nearest.getLat(),
                nearest.getLng(),
                customer.getLat(),
                customer.getLng()
        );

        double rate;
        if (distance <= 100) {
            rate = 3;
        } else if (distance <= 500) {
            rate = 2;
        } else {
            rate = 1;
        }

        double weight = 10;

        double shippingCharge = distance * rate * weight;
        shippingCharge += 10;

        if (deliverySpeed.equalsIgnoreCase("express")) {
            shippingCharge += (1.2 * weight);
        }

        return new ShippingResponse(shippingCharge, nearest.getId());
    }
}
