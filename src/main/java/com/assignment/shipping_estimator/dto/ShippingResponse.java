package com.assignment.shipping_estimator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingResponse {
    private Double shippingCharge;
    private NearestWarehouseResponse nearestWarehouse;
    private String transportMode;
    private Double distance;
    private String deliverySpeed;
}
