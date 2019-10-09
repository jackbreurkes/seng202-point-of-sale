package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;

import java.io.IOException;
import java.util.ArrayList;

public class AnalysisController {

    @FXML private ComboBox xComboBox;
    @FXML private ComboBox yComboBox;
    @FXML private VBox graphVbox;

    public void initialize() {
        xComboBox.getItems().addAll("Date", "Time", "Day");
        yComboBox.getItems().addAll("Orders");
        xComboBox.setValue("Date");
        yComboBox.setValue("Orders");
    }

    public void plotGraph() {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of orders");

        LineChart lineChart = new LineChart(xAxis, yAxis);

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Test");

        dataSeries.getData().add(new XYChart.Data( 1, 567));
        dataSeries.getData().add(new XYChart.Data( 5, 612));
        dataSeries.getData().add(new XYChart.Data(10, 800));
        dataSeries.getData().add(new XYChart.Data(20, 780));
        dataSeries.getData().add(new XYChart.Data(40, 810));
        dataSeries.getData().add(new XYChart.Data(80, 850));

        lineChart.getData().add(dataSeries);

        graphVbox.getChildren().add(lineChart);

    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToOrder(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "order.fxml", "ROSEMARY | Order Screen");
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToImportData(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "import.fxml", "ROSEMARY | Import Screen");
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToEditData(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "editData.fxml", "ROSEMARY | Edit Data Screen");
    }
}
