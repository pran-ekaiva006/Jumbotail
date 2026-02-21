package com.assignment.shipping_estimator.controller;

import com.assignment.shipping_estimator.dto.NearestWarehouseResponse;
import com.assignment.shipping_estimator.model.Warehouse;
import com.assignment.shipping_estimator.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @GetMapping("/warehouse/nearest")
    public ResponseEntity<NearestWarehouseResponse> getNearestWarehouse(
            @RequestParam Long sellerId) {
        return ResponseEntity.ok(warehouseService.findNearestWarehouse(sellerId));
    }
}
