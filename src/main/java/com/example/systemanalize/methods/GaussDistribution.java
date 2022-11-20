package com.example.systemanalize.methods;

import com.example.systemanalize.configs.DistributionConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GaussDistribution implements DistributionImitation {
    private final List<Double> generatedValues;

    private final DistributionConfig distributionConfig;

    private final static String PATH = "Имитация гауссовского распределения";

    public GaussDistribution(List<Double> generatedValues, DistributionConfig distributionConfig) {
        this.generatedValues = generatedValues;
        this.distributionConfig = distributionConfig;
    }

    @Override
    public List<Double> imitateDistribution() {
        List<Double> gauss = new ArrayList<>();

        int init = 0;
        int step = 6;
        int end = init + step;
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            while (true) {
                if (end < generatedValues.size()) {
                    double y = 0;
                    for (int i = init; i < end; i++) {
                        y += generatedValues.get(i);
                    }
                    double x = (distributionConfig.getMatOzh() + distributionConfig.getSrKvdrOtkl())
                            * Math.sqrt(2) * (y - 3);
                    if (x >= 0 && x <= 1) {
                        gauss.add(x);
                        writer.write(x + "\n");
                    }
                    init += 1;
                    end = init + 5;
                } else {
                    return gauss;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMatExpect() {
        return String.valueOf(distributionConfig.getMatOzh());
    }

    @Override
    public String getDispersion() {
        return "-";
    }

    @Override
    public String getsrKvdrOtkl() {
        return String.valueOf(distributionConfig.getSrKvdrOtkl());
    }
}
