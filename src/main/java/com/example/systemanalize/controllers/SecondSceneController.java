package com.example.systemanalize.controllers;

import com.example.systemanalize.Application;
import com.example.systemanalize.methods.DistributionImitation;
import com.example.systemanalize.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SecondSceneController implements Initializable {
    private DistributionImitation distributionImitation;

    public void setDistributionImitation(DistributionImitation distributionImitation) {
        this.distributionImitation = distributionImitation;
    }

    @FXML
    private BarChart<String, Number> barChart;

    public void back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lab 2");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void initialize() {
        List<Double> distributedValues = distributionImitation.imitateDistribution();
        //Получение интервалов вхождений для построения гистограммы
        List<Integer> listOfIntervals = Utils.getIntervals(distributedValues);
        //Построение гистограммы
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < 20; i++) {
            series.getData().add(new XYChart.Data<>("i" + (i + 1), listOfIntervals.get(i)));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
