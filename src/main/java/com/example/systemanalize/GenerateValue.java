package com.example.systemanalize;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GenerateValue {
    public static final String PATH = "SystemAnalyze";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private final Config config;
    private final double a = 0.0;
    private final double b = 1.0;

    public GenerateValue(Config config) {
        this.config = config;
    }

    public List<BigDecimal> generateValues(int loopSize) {
        List<BigDecimal> generatedValues = new ArrayList<>();
        BigDecimal RnMinusOne = config.getR();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            for (int i = 0; i < loopSize; i++) {
                BigDecimal aRnMinusOne = config.getA().multiply(RnMinusOne);
                BigDecimal Rn = aRnMinusOne.remainder(config.getM());
                BigDecimal generatedValue = Rn.divide(config.getM(), 10, RoundingMode.DOWN);
                generatedValues.add(generatedValue);
                writer.write(generatedValue + "\n");
                RnMinusOne = Rn;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return generatedValues;
    }

    public void getPL() {
        List<Integer> listOfI = new ArrayList<>();
        List<BigDecimal> generatedValues;

        generatedValues = generateValues(config.getLoopSize());
        BigDecimal Xv = generatedValues.get(99999);
        generatedValues = generateValues(config.getLoopSize() * 2);
        for (int i = 0; i < generatedValues.size(); i++) {
            if (generatedValues.get(i).equals(Xv)) {
                listOfI.add(i);
            }
        }

        int P = listOfI.get(1) - listOfI.get(0);
        for (int i = 0; i < generatedValues.size(); i++) {
            BigDecimal xi3 = generatedValues.get(i);
            BigDecimal xi3PlusP = generatedValues.get(P + i);
            if (xi3.equals(xi3PlusP)) {
                System.out.println("i3 = " + i
                        + "\nP = " + P
                        + "\nL = " + (i + P));
                return;
            }
        }
    }

    public List<Integer> getIntervals() {
        List<BigDecimal> generatedValues = generateValues(config.getLoopSize());
        List<Integer> occurrences = new ArrayList<>();
        double start = 0;
        double step = 0.05;
        double end = start + step;
        while (end < 1.05) {
            int counter = 0;
            for (BigDecimal b : generatedValues) {
                if (b.doubleValue() >= start && b.doubleValue() <= end) {
                    counter++;
                }
            }
            occurrences.add(counter);
            start += step;
            end += step;
        }
        return occurrences;
    }

    public String get2KDivideN() {
        List<BigDecimal> generatedValues = generateValues(config.getLoopSize());
        List<Double> even = new ArrayList<>();
        List<Double> odd = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < generatedValues.size(); i++) {
            if (i % 2 == 0) {
                even.add(generatedValues.get(i).doubleValue());
            } else {
                odd.add(generatedValues.get(i).doubleValue());
            }
        }

        for (int i = 0; i < generatedValues.size() / 2; i++) {
            if (odd.get(i) * odd.get(i) + even.get(i) * even.get(i) < 1) {
                counter++;
            }
        }
        return DECIMAL_FORMAT.format(2d * counter / generatedValues.size());
    }

    public String getsrKvdrOtkl() {
        return DECIMAL_FORMAT.format((b - a) / (3 * Math.sqrt(2)));
    }

    public String getDispersion() {
        return DECIMAL_FORMAT.format((a + b) / 2);
    }

    public String getMatExpect() {
        return DECIMAL_FORMAT.format((1.0) / 12);
    }
}
