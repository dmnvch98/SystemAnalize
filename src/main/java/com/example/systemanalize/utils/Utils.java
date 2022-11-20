package com.example.systemanalize.utils;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    private Utils() {
    }
    public static List<Integer> getIntervals(List<Double> generatedValues) {
        //Коллекция для хранения вхождений
        List<Integer> occurrences = new ArrayList<>();
        // Границы интервалов
        double start = 0;
        double step = 0.05;
        double end = start + step;
        while (end < 1.05) {
            int counter = 0;
            for (Double d : generatedValues) {
                // Фиксируется количество попаданий в каждый i-й интервал
                if (d>= start && d <= end) {
                    counter++;
                }
            }
            occurrences.add(counter);
            start += step;
            end += step;
        }
        return occurrences;
    }
}
