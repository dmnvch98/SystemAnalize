package com.example.systemanalize;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

public class HelloController {
    private GenerateValue generateValue;
    @FXML
    private TextField R;

    @FXML
    private TextField a;

    @FXML
    private TextField m;

    @FXML
    private TextField qty;

    @FXML
    private Button submit;

    @FXML
    private NumberAxis intervals;

    @FXML
    private CategoryAxis occurrences;

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
    void generate(ActionEvent event) {
        Config config = new Config(
//                R.getText(),
//                a.getText(),
//                m.getText(),
//                Integer.parseInt(qty.getText())
                "4294967291", "1073741824", "4294967293", 100
        );
        generateValue = new GenerateValue(config);
        List<Integer> list = generateValue.getIntervals();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < 20; i++) {
            series.getData().add(new XYChart.Data<>("i" + (i + 1), list.get(i)));
        }
        barChart.getData().add(series);
        srKvdrOtkl.setText(generateValue.getsrKvdrOtkl());
        dispersion.setText(generateValue.getDispersion());
        matExpect.setText(generateValue.getMatExpect());
        dkDivideN.setText(generateValue.get2KDivideN());
    }

}