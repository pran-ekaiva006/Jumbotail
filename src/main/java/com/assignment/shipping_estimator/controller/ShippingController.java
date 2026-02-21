package com.assignment.shipping_estimator.controller;

import com.assignment.shipping_estimator.dto.ShippingRequest;
import com.assignment.shipping_estimator.dto.ShippingResponse;
import com.assignment.shipping_estimator.service.ShippingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping("/shipping-charge")
    public ResponseEntity<Map<String, Double>> getShippingCharge(
            @RequestParam Long warehouseId,
            @RequestParam Long customerId,
            @RequestParam String deliverySpeed) {
        
        Double charge = shippingService.calculateShippingCharge(warehouseId, customerId, deliverySpeed);
        return ResponseEntity.ok(Map.of("shippingCharge", charge));
    }

    @PostMapping("/shipping-charge/calculate")
    public ResponseEntity<ShippingResponse> calculateShipping(
            @Valid @RequestBody ShippingRequest request) {
        return ResponseEntity.ok(shippingService.calculateFullShipping(request));
    }

    @GetMapping("/shipping-charge/calculate")
    public ResponseEntity<ShippingResponse> calculateShippingGet(
            @RequestParam Long sellerId,
            @RequestParam Long customerId,
            @RequestParam String deliverySpeed) {
        
        ShippingRequest request = ShippingRequest.builder()
                .sellerId(sellerId)
                .customerId(customerId)
                .deliverySpeed(deliverySpeed)
                .build();
        
        return ResponseEntity.ok(shippingService.calculateFullShipping(request));
    }
}
