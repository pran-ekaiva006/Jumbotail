package com.assignment.shipping_estimator.controller;

import com.assignment.shipping_estimator.model.Seller;
import com.assignment.shipping_estimator.model.Warehouse;
import com.assignment.shipping_estimator.repository.SellerRepository;
import com.assignment.shipping_estimator.repository.WarehouseRepository;
import com.assignment.shipping_estimator.util.DistanceUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;

    public WarehouseController(WarehouseRepository warehouseRepository,
                               SellerRepository sellerRepository) {
        this.warehouseRepository = warehouseRepository;
        this.sellerRepository = sellerRepository;
    }

    // Existing API
    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // Nearest warehouse API
    @GetMapping("/warehouse/nearest")
    public Warehouse getNearestWarehouse(@RequestParam Long sellerId) {

        Seller seller = sellerRepository.findById(sellerId).orElse(null);

        if (seller == null) {
            return null;
        }

        List<Warehouse> warehouses = warehouseRepository.findAll();

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {

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

        return nearest;
    }
}


