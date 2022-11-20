package com.example.systemanalize.methods;

import com.example.systemanalize.configs.DistributionConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GammaDistribution implements DistributionImitation {
    private final List<Double> generatedValues;

    private final DistributionConfig distributionConfig;

    private final static String PATH = "Имитация гамма-распределения";

    public GammaDistribution(List<Double> generatedValues, DistributionConfig distributionConfig) {
        this.generatedValues = generatedValues;
        this.distributionConfig = distributionConfig;
    }

    @Override
    public List<Double> imitateDistribution() {
        List<Double> result = new ArrayList<>();
        int init = 0;
        int end = init + distributionConfig.getEta();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            while (true) {
                if (end < generatedValues.size()) {
                    double y = 0;
                    for (int i = init; i < end; i++) {
                        y += generatedValues.get(i);
                    }
                    double x = (-1 / distributionConfig.getLambda()) * Math.log10(y);
                    if (x >= 0 && x <= 1) {
                        result.add(x);
                        writer.write(x + "\n");
                    }
                    init += 1;
                    end += 1;
                } else {
                    return result;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMatExpect() {
        double result = distributionConfig.getEta() / distributionConfig.getLambda();
        return String.valueOf(result);
    }

    @Override
    public String getDispersion() {
        double result = distributionConfig.getEta() / (distributionConfig.getLambda() * distributionConfig.getLambda());
        return String.valueOf(result);
    }

    @Override
    public String getsrKvdrOtkl() {
        return "-";
    }
}
