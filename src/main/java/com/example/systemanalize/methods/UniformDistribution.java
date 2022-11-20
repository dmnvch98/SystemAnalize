package com.example.systemanalize.methods;

import java.util.List;

public class UniformDistribution implements DistributionImitation {
    private final List<Double> generatedValues;

    public UniformDistribution(List<Double> generatedValues) {
        this.generatedValues = generatedValues;
    }

    @Override
    public List<Double> imitateDistribution() {
        double a = 0.00;
        double b = 1.00;
        return generatedValues.stream()
                .map(v -> a + (b - a) * v)
                .toList();
    }
}
