package com.example.systemanalize.controllers;

import com.example.systemanalize.Application;
import com.example.systemanalize.configs.GenerateValueConfig;
import com.example.systemanalize.GenerateValue;
import com.example.systemanalize.configs.DistributionConfig;
import com.example.systemanalize.methods.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.*;

public class MainSceneController {
    @FXML
    private ChoiceBox<String> selectMethod;
    @FXML
    private TextField R;

    @FXML
    private TextField a;

    @FXML
    private TextField m;

    @FXML
    private TextField qty;

    @FXML
    private TextField SrKvdrOtkl;

    @FXML
    private TextField lambda;

    @FXML
    private TextField matOzh;

    @FXML
    private TextField eta;

    public void switchToSecondScene(ActionEvent event, String selectedMethod, DistributionImitation distributionImitation)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("secondScene.fxml"));
        Parent root = fxmlLoader.load();

        SecondSceneController secondSceneController = fxmlLoader.getController();
        secondSceneController.setDistributionImitation(distributionImitation);
        secondSceneController.initialize();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(selectedMethod);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        List<String> methods = Arrays.asList("Равномерное распределение", "Распределение Гаусса",
                "Экспоненциальное распределение", "Гамма распределение", "Треугольное распределение");
        selectMethod.getItems().addAll(methods);
    }

    public void start(ActionEvent event) throws IOException {
        GenerateValueConfig config = new GenerateValueConfig(
                //Чтение введеных "магических чисел"
                R.getText(),
                a.getText(),
                m.getText(),
                Integer.parseInt(qty.getText())
          //      "4294967291", "1073741824", "4294967293", 100000
        );
        GenerateValue generateValue = new GenerateValue(config);
        DistributionImitation distributionImitation = null;
        DistributionConfig distributionConfig = new DistributionConfig();
        String selectedMethod = selectMethod.getValue();
        switch(selectedMethod) {
            case "Распределение Гаусса" -> {
                distributionConfig.setMatOzh(Double.parseDouble(matOzh.getText()));
                distributionConfig.setSrKvdrOtkl(Double.parseDouble(SrKvdrOtkl.getText()));
                distributionImitation = new GaussDistribution(generateValue.getGeneratedValues(), distributionConfig);
            }
            case "Равномерное распределение" -> distributionImitation =
                    new UniformDistribution(generateValue.getGeneratedValues());
            case "Экспоненциальное распределение" -> {
                distributionConfig.setLambda(Double.parseDouble(lambda.getText()));
                distributionImitation = new ExponentDistribution(generateValue.getGeneratedValues(), distributionConfig);
            }
            case "Гамма распределение" -> {
                distributionConfig.setLambda(Double.parseDouble(lambda.getText()));
                distributionConfig.setEta(Integer.parseInt(eta.getText()));
                distributionImitation = new GammaDistribution(generateValue.getGeneratedValues(), distributionConfig);
            }
            case "Треугольное распределение" -> distributionImitation
                    = new TriangleDistribution(generateValue.getGeneratedValues());
        }
        switchToSecondScene(event, selectedMethod, distributionImitation);
    }
}
