package com.assignment.shipping_estimator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearestWarehouseResponse {
    private Long warehouseId;
    private String warehouseName;
    private LocationDto warehouseLocation;
}
