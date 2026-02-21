package com.assignment.shipping_estimator.dto;

public class ShippingResponse {

    private double shippingCharge;
    private Long warehouseId;

    public ShippingResponse(double shippingCharge, Long warehouseId) {
        this.shippingCharge = shippingCharge;
        this.warehouseId = warehouseId;
    }

    public double getShippingCharge() {
        return shippingCharge;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }
}

