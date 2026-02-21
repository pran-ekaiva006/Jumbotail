package com.assignment.shipping_estimator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Shipping Charge Estimator");
        response.put("version", "1.0.0");
        response.put("status", "Running");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("getAllWarehouses", "GET /api/v1/warehouses");
        endpoints.put("getNearestWarehouse", "GET /api/v1/warehouse/nearest?sellerId={id}");
        endpoints.put("getShippingCharge", "GET /api/v1/shipping-charge?warehouseId={id}&customerId={id}&deliverySpeed={standard|express}");
        endpoints.put("calculateFullShipping", "GET /api/v1/shipping-charge/calculate?sellerId={id}&customerId={id}&deliverySpeed={standard|express}");
        endpoints.put("calculateFullShippingPost", "POST /api/v1/shipping-charge/calculate");
        endpoints.put("h2Console", "GET /h2-console");
        
        response.put("endpoints", endpoints);
        return ResponseEntity.ok(response);
    }
}
