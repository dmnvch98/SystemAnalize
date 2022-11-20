package com.example.systemanalize.methods;

import com.example.systemanalize.configs.DistributionConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExponentDistribution implements DistributionImitation {
    private final List<Double> generatedValues;
    private final DistributionConfig distributionConfig;

    private final static String PATH = "Имитация экспоненциального распределения";

    public ExponentDistribution(List<Double> generatedValues, DistributionConfig distributionConfig) {
        this.generatedValues = generatedValues;
        this.distributionConfig = distributionConfig;
    }

    @Override
    public List<Double> imitateDistribution() {
        List<Double> result = new ArrayList<>();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            for (Double value : generatedValues) {
                double X = (-1 / distributionConfig.getLambda()) * Math.log10(value);
                if (X >= 0 && X <= 1) {
                    result.add(X);
                    writer.write(X + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public String getMatExpect() {
        return String.valueOf(1 / distributionConfig.getLambda());
    }

    @Override
    public String getDispersion() {
        return "-";
    }

    @Override
    public String getsrKvdrOtkl() {
        return String.valueOf(1 / distributionConfig.getLambda());
    }
}
