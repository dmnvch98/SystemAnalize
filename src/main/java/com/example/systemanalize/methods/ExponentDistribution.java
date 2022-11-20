package com.example.systemanalize.methods;

import com.example.systemanalize.configs.DistributionConfig;

import java.util.ArrayList;
import java.util.List;

public class ExponentDistribution implements DistributionImitation {
    private final List<Double> generatedValues;
    private final DistributionConfig distributionConfig;

    public ExponentDistribution(List<Double> generatedValues, DistributionConfig distributionConfig) {
        this.generatedValues = generatedValues;
        this.distributionConfig = distributionConfig;
    }

    @Override
    public List<Double> imitateDistribution() {
        List<Double> result = new ArrayList<>();
        for (Double value: generatedValues) {
            double X = (-1 / distributionConfig.getLambda()) * Math.log10(value);
            if (X >= 0 && X <= 1) {
                result.add(X);
            }
        }
        return result;
    }
}
