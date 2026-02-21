package com.assignment.shipping_estimator.strategy;

import org.springframework.stereotype.Component;

@Component
public class MiniVanStrategy implements TransportStrategy {
    
    private static final double RATE = 3.0;
    private static final double MIN_DISTANCE = 0;
    private static final double MAX_DISTANCE = 100;

    @Override
    public String getModeName() {
        return "MINI_VAN";
    }

    @Override
    public double getRatePerKmPerKg() {
        return RATE;
    }

    @Override
    public boolean isApplicable(double distance) {
        return distance >= MIN_DISTANCE && distance <= MAX_DISTANCE;
    }
}
