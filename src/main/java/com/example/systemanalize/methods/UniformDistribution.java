package com.example.systemanalize.methods;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UniformDistribution implements DistributionImitation {
    private final List<Double> generatedValues;
    //Начало интервала
    private final double a = 0.0;
    //Конец интервала
    private final double b = 1.0;

    private final static String PATH = "Имитация равномерного распределения";

    public UniformDistribution(List<Double> generatedValues) {
        this.generatedValues = generatedValues;
    }

    @Override
    public List<Double> imitateDistribution() {
        double a = 0.00;
        double b = 1.00;
        List<Double> result = new ArrayList<>();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            for (Double value: generatedValues) {
                double resultValue = a + (b - a) * value;
                result.add(resultValue);
                writer.write(resultValue + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
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
