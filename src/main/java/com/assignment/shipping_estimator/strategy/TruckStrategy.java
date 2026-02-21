package com.assignment.shipping_estimator.strategy;

import org.springframework.stereotype.Component;

@Component
public class TruckStrategy implements TransportStrategy {
    
    private static final double RATE = 2.0;
    private static final double MIN_DISTANCE = 100;
    private static final double MAX_DISTANCE = 500;

    @Override
    public String getModeName() {
        return "TRUCK";
    }

    @Override
    public double getRatePerKmPerKg() {
        return RATE;
    }

    @Override
    public boolean isApplicable(double distance) {
        return distance > MIN_DISTANCE && distance <= MAX_DISTANCE;
    }
}
