package com.assignment.shipping_estimator.config;

import com.assignment.shipping_estimator.model.Customer;
import com.assignment.shipping_estimator.model.Product;
import com.assignment.shipping_estimator.model.Seller;
import com.assignment.shipping_estimator.model.Warehouse;
import com.assignment.shipping_estimator.repository.CustomerRepository;
import com.assignment.shipping_estimator.repository.ProductRepository;
import com.assignment.shipping_estimator.repository.SellerRepository;
import com.assignment.shipping_estimator.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing sample data...");

        Warehouse blrWarehouse = warehouseRepository.save(Warehouse.builder()
                .name("BLR_Warehouse")
                .latitude(12.9716)
                .longitude(77.5946)
                .build());

        Warehouse mumbWarehouse = warehouseRepository.save(Warehouse.builder()
                .name("MUMB_Warehouse")
                .latitude(19.0760)
                .longitude(72.8777)
                .build());

        Warehouse delWarehouse = warehouseRepository.save(Warehouse.builder()
                .name("DEL_Warehouse")
                .latitude(28.7041)
                .longitude(77.1025)
                .build());

        Seller nestleSeller = sellerRepository.save(Seller.builder()
                .name("Nestle Seller")
                .latitude(12.9352)
                .longitude(77.6245)
                .build());

        Seller riceSeller = sellerRepository.save(Seller.builder()
                .name("Rice Seller")
                .latitude(19.1136)
                .longitude(72.8697)
                .build());

        Seller sugarSeller = sellerRepository.save(Seller.builder()
                .name("Sugar Seller")
                .latitude(28.6139)
                .longitude(77.2090)
                .build());

        Customer customer1 = customerRepository.save(Customer.builder()
                .name("Kirana Store 1")
                .latitude(13.0827)
                .longitude(80.2707)
                .build());

        Customer customer2 = customerRepository.save(Customer.builder()
                .name("Kirana Store 2")
                .latitude(18.5204)
                .longitude(73.8567)
                .build());

        Customer customer3 = customerRepository.save(Customer.builder()
                .name("Kirana Store 3")
                .latitude(22.5726)
                .longitude(88.3639)
                .build());

        productRepository.save(Product.builder()
                .name("Maggie 500g")
                .weightKg(0.5)
                .seller(nestleSeller)
                .build());

        productRepository.save(Product.builder()
                .name("Rice Bag 10Kg")
                .weightKg(10.0)
                .seller(riceSeller)
                .build());

        productRepository.save(Product.builder()
                .name("Sugar Bag 25Kg")
                .weightKg(25.0)
                .seller(sugarSeller)
                .build());

        log.info("Sample data initialized successfully!");
        log.info("Warehouses: {}", warehouseRepository.count());
        log.info("Sellers: {}", sellerRepository.count());
        log.info("Customers: {}", customerRepository.count());
        log.info("Products: {}", productRepository.count());
    }
}
