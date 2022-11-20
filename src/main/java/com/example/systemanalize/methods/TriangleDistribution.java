package com.example.systemanalize.methods;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TriangleDistribution implements DistributionImitation {
    private final List<Double> generatedValues;

    private final static String PATH = "Имитация треугольного распределения";

    double a = 0;
    double b = 1;

    public TriangleDistribution(List<Double> generatedValues) {
        this.generatedValues = generatedValues;
    }

    @Override
    public List<Double> imitateDistribution() {
        List<Double> result = new ArrayList<>();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            for (int i = 0; i < generatedValues.size(); i++) {
                if (i < generatedValues.size() - 1) {
                    if (generatedValues.get(i) < generatedValues.get(i + 1)) {
                        double resultValue = a + (b - a) * generatedValues.get(i);
                        result.add(resultValue);
                        writer.write(resultValue + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public String getMatExpect() {
        return null;
    }

    @Override
    public String getDispersion() {
        return null;
    }

    @Override
    public String getsrKvdrOtkl() {
        return null;
    }
}
