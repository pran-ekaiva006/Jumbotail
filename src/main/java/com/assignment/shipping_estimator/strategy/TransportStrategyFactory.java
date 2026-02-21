package com.assignment.shipping_estimator.strategy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransportStrategyFactory {

    private final List<TransportStrategy> strategies;

    public TransportStrategyFactory(List<TransportStrategy> strategies) {
        this.strategies = strategies;
    }

    public TransportStrategy getStrategy(double distance) {
        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(distance))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No transport strategy found for distance: " + distance));
    }
}
