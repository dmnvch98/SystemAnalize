package com.example.systemanalize.methods;

import java.util.List;

public class UniformDistribution implements DistributionImitation {
    private final List<Double> generatedValues;
    //Начало интервала
    private final double a = 0.0;
    //Конец интервала
    private final double b = 1.0;

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

    @Override
    public String getMatExpect() {
        return String.valueOf((a + b) / 2);
    }

    @Override
    public String getDispersion() {
        double result = ((b - a) * (b - a)) / 12;
        return String.valueOf(result);
    }

    @Override
    public String getsrKvdrOtkl() {
        return "-";
    }
}
