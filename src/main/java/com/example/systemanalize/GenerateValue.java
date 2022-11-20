package com.example.systemanalize;

import com.example.systemanalize.configs.GenerateValueConfig;

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
    //Название файла, куда будут сохраняться сгенерированные значения. Путь - корень проекта
    public static final String PATH = "SystemAnalyze";
    //Вспомогательная переменная для округления чисел
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    //Вспомогательная переменная для генерации чисел (хранит 3 "магических числа")
    private final GenerateValueConfig config;
    //Начало интервала
    private final double a = 0.0;
    //Конец интервала
    private final double b = 1.0;
    //Коллекция сгенерированных чисел
    private final List<Double> generatedValues;

    public GenerateValue(GenerateValueConfig config) {
        this.config = config;
        //При инциализации объекта, вызывается метод для генерации чисел
        generatedValues = generateValues();
    }

    public List<Double> generateValues() {
        List<Double> generatedValues = new ArrayList<>();
        BigDecimal RnMinusOne = config.getR();
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(PATH), StandardCharsets.UTF_8)) {
            for (int i = 0; i < config.getLoopSize(); i++) {
                //Шаг 1. Коэффициент a умножается на число Rn-1 ;
                BigDecimal aRnMinusOne = config.getA().multiply(RnMinusOne);
                //Шаг 2. Результат умножения aRn-1 делится на m и извлекается остаток
                BigDecimal Rn = aRnMinusOne.remainder(config.getM());
                //Остаток от деления Rn делится на m, чтобы получить искомое случайное число между нулем и единицей
                BigDecimal generatedValue = Rn.divide(config.getM(), 10, RoundingMode.DOWN);
                //Добавление числа в коллекцию
                generatedValues.add(generatedValue.doubleValue());
                //Запись числа в файл
                writer.write(generatedValue + "\n");
                //Для получения следующего числа в качестве Rn-1 принимается остаток от деления Rn, полученный на втором шаге
                RnMinusOne = Rn;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return generatedValues;
    }

    public List<Double> getGeneratedValues() {
        return generatedValues;
    }
}
