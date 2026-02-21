package com.assignment.shipping_estimator.strategy;

public interface TransportStrategy {
    String getModeName();
    double getRatePerKmPerKg();
    boolean isApplicable(double distance);
}
