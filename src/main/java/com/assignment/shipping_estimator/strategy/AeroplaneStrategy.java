package com.assignment.shipping_estimator.strategy;

import org.springframework.stereotype.Component;

@Component
public class AeroplaneStrategy implements TransportStrategy {
    
    private static final double RATE = 1.0;
    private static final double MIN_DISTANCE = 500;

    @Override
    public String getModeName() {
        return "AEROPLANE";
    }

    @Override
    public double getRatePerKmPerKg() {
        return RATE;
    }

    @Override
    public boolean isApplicable(double distance) {
        return distance > MIN_DISTANCE;
    }
}
