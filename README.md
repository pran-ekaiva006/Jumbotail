# Shipping Charge Estimator

A Spring Boot application for calculating shipping charges in a B2B e-commerce marketplace that helps Kirana stores discover and order products.

## Author

**Pranjal Kumar Verma**

---

## Features

- **Nearest Warehouse Finder**: Find the nearest warehouse for a seller based on location
- **Shipping Charge Calculator**: Calculate shipping charges based on distance, weight, transport mode, and delivery speed
- **Strategy Pattern**: Flexible transport mode selection (Mini Van, Truck, Aeroplane)
- **Caching**: Response caching using Caffeine for improved performance
- **Exception Handling**: Comprehensive error handling with meaningful error messages

---

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming Language |
| Spring Boot | 3.2.0 | Framework |
| Spring Data JPA | 3.2.0 | Database ORM |
| H2 Database | 2.2.x | In-memory Database |
| Caffeine Cache | 3.1.x | Caching |
| Lombok | 1.18.30 | Boilerplate Reduction |
| Maven | 3.9.x | Build Tool |

---

## Business Logic

### Transport Modes

| Distance (km) | Transport Mode | Rate (per km per kg) |
|---------------|----------------|----------------------|
| 0 - 100       | Mini Van       | ₹3.0                 |
| 100 - 500     | Truck          | ₹2.0                 |
| 500+          | Aeroplane      | ₹1.0                 |

### Delivery Speeds

- **Standard**: ₹10 base charge + calculated shipping charge
- **Express**: ₹10 base charge + ₹1.2 per kg extra + calculated shipping charge

### Distance Calculation

Uses the **Haversine formula** to calculate great-circle distance between two geographic coordinates.

---

## Project Structure

```
src/main/java/com/assignment/shipping_estimator/
├── ShippingEstimatorApplication.java
├── config/
│   ├── CacheConfig.java
│   └── DataInitializer.java
├── controller/
│   ├── HomeController.java
│   ├── ShippingController.java
│   └── WarehouseController.java
├── dto/
│   ├── LocationDto.java
│   ├── NearestWarehouseResponse.java
│   ├── ShippingRequest.java
│   └── ShippingResponse.java
├── exception/
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   ├── InvalidRequestException.java
│   └── ResourceNotFoundException.java
├── model/
│   ├── Customer.java
│   ├── Product.java
│   ├── Seller.java
│   └── Warehouse.java
├── repository/
│   ├── CustomerRepository.java
│   ├── ProductRepository.java
│   ├── SellerRepository.java
│   └── WarehouseRepository.java
├── service/
│   ├── ShippingService.java
│   └── WarehouseService.java
├── strategy/
│   ├── AeroplaneStrategy.java
│   ├── MiniVanStrategy.java
│   ├── TransportStrategy.java
│   ├── TransportStrategyFactory.java
│   └── TruckStrategy.java
└── util/
    └── DistanceUtil.java
```

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Build and Run

```bash
# Navigate to project directory
cd shipping-estimator

# Make mvnw executable (first time only)
chmod +x ./mvnw

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start at `http://localhost:8080`

---

## API Endpoints

### Base URL

```
http://localhost:8080/api/v1
```

### 1. Home - API Information

```
GET /
```

**Example:**
```bash
curl http://localhost:8080/
```

---

### 2. Get All Warehouses

```
GET /api/v1/warehouses
```

**Example:**
```bash
curl http://localhost:8080/api/v1/warehouses
```

**Response:**
```json
[
  {"id": 1, "name": "BLR_Warehouse", "latitude": 12.9716, "longitude": 77.5946},
  {"id": 2, "name": "MUMB_Warehouse", "latitude": 19.076, "longitude": 72.8777},
  {"id": 3, "name": "DEL_Warehouse", "latitude": 28.7041, "longitude": 77.1025}
]
```

---

### 3. Get Warehouse by ID

```
GET /api/v1/warehouse/{id}
```

**Example:**
```bash
curl http://localhost:8080/api/v1/warehouse/1
```

---

### 4. Find Nearest Warehouse

```
GET /api/v1/warehouse/nearest?sellerId={id}
```

**Example:**
```bash
curl "http://localhost:8080/api/v1/warehouse/nearest?sellerId=1"
```

**Response:**
```json
{
  "warehouseId": 1,
  "warehouseName": "BLR_Warehouse",
  "warehouseLocation": {"lat": 12.9716, "lng": 77.5946}
}
```

---

### 5. Calculate Shipping Charge (Simple)

```
GET /api/v1/shipping-charge?warehouseId={id}&customerId={id}&deliverySpeed={standard|express}
```

**Example:**
```bash
curl "http://localhost:8080/api/v1/shipping-charge?warehouseId=1&customerId=1&deliverySpeed=standard"
```

**Response:**
```json
{
  "shippingCharge": 2910.15
}
```

---

### 6. Calculate Full Shipping (GET)

```
GET /api/v1/shipping-charge/calculate?sellerId={id}&customerId={id}&deliverySpeed={standard|express}
```

**Example:**
```bash
curl "http://localhost:8080/api/v1/shipping-charge/calculate?sellerId=1&customerId=1&deliverySpeed=standard"
```

**Response:**
```json
{
  "shippingCharge": 2910.15,
  "nearestWarehouse": {
    "warehouseId": 1,
    "warehouseName": "BLR_Warehouse",
    "warehouseLocation": {"lat": 12.9716, "lng": 77.5946}
  },
  "transportMode": "TRUCK",
  "distance": 290.01,
  "deliverySpeed": "STANDARD"
}
```

---

### 7. Calculate Full Shipping (POST)

```
POST /api/v1/shipping-charge/calculate
Content-Type: application/json
```

**Request Body:**
```json
{
  "sellerId": 1,
  "customerId": 1,
  "deliverySpeed": "express"
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/v1/shipping-charge/calculate \
  -H "Content-Type: application/json" \
  -d '{"sellerId": 1, "customerId": 1, "deliverySpeed": "express"}'
```

---

## Sample Data

The application automatically initializes sample data on startup:

### Warehouses

| ID | Name | Location (Lat, Lng) |
|----|------|---------------------|
| 1  | BLR_Warehouse | 12.9716, 77.5946 |
| 2  | MUMB_Warehouse | 19.0760, 72.8777 |
| 3  | DEL_Warehouse | 28.7041, 77.1025 |

### Sellers

| ID | Name | Location (Lat, Lng) |
|----|------|---------------------|
| 1  | Nestle Seller | 12.9352, 77.6245 |
| 2  | Rice Seller | 19.1136, 72.8697 |
| 3  | Sugar Seller | 28.6139, 77.2090 |

### Customers

| ID | Name | Location (Lat, Lng) |
|----|------|---------------------|
| 1  | Kirana Store 1 | 13.0827, 80.2707 |
| 2  | Kirana Store 2 | 18.5204, 73.8567 |
| 3  | Kirana Store 3 | 22.5726, 88.3639 |

---

## H2 Database Console

Access the H2 console at: `http://localhost:8080/h2-console`

| Setting | Value |
|---------|-------|
| JDBC URL | `jdbc:h2:mem:shippingdb` |
| Username | `sa` |
| Password | *(leave empty)* |

---

## Caching

The application uses **Caffeine Cache** for performance optimization:

- **Maximum Size**: 500 entries
- **Expiration**: 10 minutes after write

### Cached Operations

| Cache Name | Description |
|------------|-------------|
| `nearestWarehouse` | Nearest warehouse lookups |
| `shippingCharge` | Shipping charge calculations |

---

## Design Patterns Used

1. **Strategy Pattern** - Transport mode selection based on distance
2. **Repository Pattern** - Data access abstraction
3. **DTO Pattern** - Data transfer objects for API requests/responses
4. **Service Layer Pattern** - Business logic separation
5. **Factory Pattern** - Transport strategy creation

---

## Error Handling

The application includes comprehensive exception handling:

| Exception | HTTP Status | Description |
|-----------|-------------|-------------|
| `ResourceNotFoundException` | 404 | When requested resource is not found |
| `InvalidRequestException` | 400 | When request parameters are invalid |
| `IllegalArgumentException` | 400 | For invalid arguments |

**Error Response Format:**
```json
{
  "timestamp": "2026-02-21T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with id: '999'",
  "path": "/api/v1/shipping-charge/calculate"
}
```

---

## License

This project is for Jumbotail assignment.


