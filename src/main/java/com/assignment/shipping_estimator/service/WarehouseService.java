package com.assignment.shipping_estimator.service;

import com.assignment.shipping_estimator.dto.LocationDto;
import com.assignment.shipping_estimator.dto.NearestWarehouseResponse;
import com.assignment.shipping_estimator.exception.ResourceNotFoundException;
import com.assignment.shipping_estimator.model.Seller;
import com.assignment.shipping_estimator.model.Warehouse;
import com.assignment.shipping_estimator.repository.SellerRepository;
import com.assignment.shipping_estimator.repository.WarehouseRepository;
import com.assignment.shipping_estimator.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));
    }

    @Cacheable(value = "nearestWarehouse", key = "#sellerId")
    public NearestWarehouseResponse findNearestWarehouse(Long sellerId) {
        log.debug("Finding nearest warehouse for seller: {}", sellerId);
        
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", "id", sellerId));

        List<Warehouse> warehouses = warehouseRepository.findAll();
        
        if (warehouses.isEmpty()) {
            throw new ResourceNotFoundException("No warehouses available");
        }

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {
            double distance = DistanceUtil.calculateDistance(
                    seller.getLatitude(), seller.getLongitude(),
                    warehouse.getLatitude(), warehouse.getLongitude()
            );
            
            if (distance < minDistance) {
                minDistance = distance;
                nearest = warehouse;
            }
        }

        return NearestWarehouseResponse.builder()
                .warehouseId(nearest.getId())
                .warehouseName(nearest.getName())
                .warehouseLocation(LocationDto.builder()
                        .lat(nearest.getLatitude())
                        .lng(nearest.getLongitude())
                        .build())
                .build();
    }
}
