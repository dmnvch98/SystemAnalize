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

//    public Map<String, Integer> getPL() {
//        Map<String, Integer> resultMap = new HashMap<>();
//        List<Integer> listOfI = new ArrayList<>();
//        // Извлечение последнего элемента из коллекции.
//        // generatedValues.size() - 1 потому-что в коллекции индексация начинается с 0
//        BigDecimal Xv = generatedValues.get(generatedValues.size() - 1);
//        // Проходим по всей коллекции и ищем числа равные Xv.Если такое есть, то сохраняем его индекс в лист
//        for (int i = 0; i < generatedValues.size(); i++) {
//            if (generatedValues.get(i).equals(Xv)) {
//                listOfI.add(i);
//            }
//        }
//        //Вычисляем i3, P, L, если число индексов 2 и более.
//        //Если индексов меньше, значит нужно указать большее кол-во генерируемых элементов
//        if (listOfI.size() > 1) {
//            // P = i2 - i1
//            int P = listOfI.get(1) - listOfI.get(0);
//            resultMap.put("i1", listOfI.get(0));
//            resultMap.put("i2", listOfI.get(1));
//            resultMap.put("P", P);
//            //Поиск минимального i3, для которого выполняется xi3=xi3+p.
//            for (int i = 0; i < generatedValues.size(); i++) {
//                BigDecimal xi3 = generatedValues.get(i);
//                BigDecimal xi3PlusP = generatedValues.get(P + i);
//                if (xi3.equals(xi3PlusP)) {
//                    resultMap.put("i3", i);
//                    resultMap.put("L", i + P);
//                    return resultMap;
//                }
//            }
//        }
//        return resultMap;
//    }

//    public List<Integer> getIntervals(List<BigDecimal> generatedValues) {
//        //Коллекция для хранения вхождений
//        List<Integer> occurrences = new ArrayList<>();
//        // Границы интервалов
//        double start = 0;
//        double step = 0.05;
//        double end = start + step;
//        while (end < 1.05) {
//            int counter = 0;
//            for (BigDecimal b : generatedValues) {
//                // Фиксируется количество попаданий в каждый i-й интервал
//                if (b.doubleValue() >= start && b.doubleValue() <= end) {
//                    counter++;
//                }
//            }
//            occurrences.add(counter);
//            start += step;
//            end += step;
//        }
//        return occurrences;
//    }

    public String get2KDivideN() {
        List<Double> even = new ArrayList<>();
        List<Double> odd = new ArrayList<>();
        int counter = 0;
        //Фильтрация сгенерируемых чисел по их индексу. Четные идут в even, нечетные - odd
        for (int i = 0; i < generatedValues.size(); i++) {
            if (i % 2 == 0) {
                even.add(generatedValues.get(i).doubleValue());
            } else {
                odd.add(generatedValues.get(i).doubleValue());
            }
        }

        for (int i = 0; i < generatedValues.size() / 2; i++) {
            //Проверяется выполнение условия (x2i-1)^2 + (x2i)^2 < 1
            //(x2i-1) - нечетные индексы, (x2i) - четные
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

    public List<Double> getGeneratedValues() {
        return generatedValues;
    }
}
