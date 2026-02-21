# Shipping Charge Estimator

A Spring Boot based backend application to calculate shipping charges in a B2B e-commerce marketplace.

## Author
**Pranjal Kumar Verma**

## Tech Stack
- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (In-memory)
- Maven

## Features
- Calculate shipping charges based on distance
- Find nearest warehouse to a seller
- Support for Standard and Express delivery speeds
- Distance calculation using Haversine formula

## How to Run

```bash
cd shipping-estimator
chmod +x ./mvnw
./mvnw spring-boot:run
```

The application will start at `http://localhost:8080`

## API Endpoints

### 1. Get All Warehouses
```
GET /warehouses
```

### 2. Find Nearest Warehouse to Seller
```
GET /warehouse/nearest?sellerId={sellerId}
```

### 3. Calculate Shipping Charge (with Warehouse ID)
```
GET /shipping-charge?warehouseId={warehouseId}&customerId={customerId}&deliverySpeed={standard|express}
```

### 4. Calculate Shipping Charge (Full Calculation)
```
GET /shipping-charge/calculate?sellerId={sellerId}&customerId={customerId}&deliverySpeed={standard|express}
```

## Sample API Calls

```bash
# Get all warehouses
curl http://localhost:8080/warehouses

# Find nearest warehouse for seller 1
curl http://localhost:8080/warehouse/nearest?sellerId=1

# Calculate shipping charge
curl "http://localhost:8080/shipping-charge/calculate?sellerId=1&customerId=1&deliverySpeed=standard"

# Calculate express shipping
curl "http://localhost:8080/shipping-charge/calculate?sellerId=1&customerId=1&deliverySpeed=express"
```

## H2 Database Console

Access the H2 console at: `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

## Shipping Rate Logic

| Distance (km) | Rate (per km per kg) |
|---------------|----------------------|
| 0 - 100       | ₹3                   |
| 101 - 500     | ₹2                   |
| 500+          | ₹1                   |

- Base charge: ₹10
- Express delivery surcharge: ₹1.2 per kg

## Project Structure

```
src/main/java/com/assignment/shipping_estimator/
├── ShippingEstimatorApplication.java
├── controller/
│   ├── ShippingController.java
│   └── WarehouseController.java
├── dto/
│   └── ShippingResponse.java
├── model/
│   ├── Customer.java
│   ├── Seller.java
│   └── Warehouse.java
├── repository/
│   ├── CustomerRepository.java
│   ├── SellerRepository.java
│   └── WarehouseRepository.java
└── util/
    └── DistanceUtil.java
```


