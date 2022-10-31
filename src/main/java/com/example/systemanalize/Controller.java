package com.example.systemanalize;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

public class Controller {
    @FXML
    private TextField R;

    @FXML
    private TextField a;

    @FXML
    private TextField m;

    @FXML
    private TextField qty;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Text dispersion;

    @FXML
    private Text matExpect;

    @FXML
    private Text srKvdrOtkl;

    @FXML
    private Text dkDivideN;

    @FXML
    private Text i1;

    @FXML
    private Text i2;

    @FXML
    private Text i3;

    @FXML
    private Text L;

    @FXML
    private Text P;

    @FXML
    void generate() {
        //Создание вспомогательного объекта для генерации чисел
        Config config = new Config(
                //Чтение введеных "магических чисел"
                R.getText(),
                a.getText(),
                m.getText(),
                Integer.parseInt(qty.getText())
           //     "4294967291", "1073741824", "4294967293"
        );
        // Создание класса для генерации чисел
        GenerateValue generateValue = new GenerateValue(config);
        //Получение интервалов вхождений для построения гистограммы
        List<Integer> listOfIntervals = generateValue.getIntervals();
        //Построение гистограммы
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < 20; i++) {
            series.getData().add(new XYChart.Data<>("i" + (i + 1), listOfIntervals.get(i)));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
        //Установка след. значений: Среднее квадратичное отклонение, Дисперсия, Математическое ожидание
        srKvdrOtkl.setText(generateValue.getsrKvdrOtkl());
        dispersion.setText(generateValue.getDispersion());
        matExpect.setText(generateValue.getMatExpect());
        dkDivideN.setText(generateValue.get2KDivideN());
        //Получение и установка i1, i2, i3, P, L
        Map<String, Integer> iPLmap = generateValue.getPL();
        i1.setText(iPLmap.getOrDefault("i1", 0).toString());
        i2.setText(iPLmap.getOrDefault("i2", 0).toString());
        i3.setText(iPLmap.getOrDefault("i3", 0).toString());
        P.setText(iPLmap.getOrDefault("P", 0).toString());
        L.setText(iPLmap.getOrDefault("L", 0).toString());
    }

}