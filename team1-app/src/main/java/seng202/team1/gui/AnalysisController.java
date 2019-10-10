package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import seng202.team1.data.JDBCStorage;
import seng202.team1.model.Order;



import java.io.IOException;

import java.util.*;

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

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(String.valueOf(xComboBox.getValue()));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(String.valueOf(yComboBox.getValue()));

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis,yAxis);

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName(String.valueOf(yComboBox.getValue()) + " against " + String.valueOf(xComboBox.getValue()));

        addDataToSeries(dataSeries, String.valueOf(xComboBox.getValue()), String.valueOf(yComboBox.getValue()));

        lineChart.getData().add(dataSeries);

        graphVbox.getChildren().removeAll(graphVbox.getChildren());
        graphVbox.getChildren().add(lineChart);

    }

    public void addDataToSeries(XYChart.Series dataSeries, String xValue, String yValue) {
        JDBCStorage memory = JDBCStorage.getInstance();
        Set<Order> allOrders = memory.getAllOrders();
        ArrayList<Date> counted = new ArrayList<>();
        ArrayList<String> toBeSorted = new ArrayList<>();

        Iterator<Order> iterator1 = allOrders.iterator();
        int count = 0;

        while (iterator1.hasNext()) {
            Order order1 = iterator1.next();
            Date date1 = order1.getLastUpdated();
            date1 = dateOnly(date1);
            if (!counted.contains(date1)) {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(date1);
                count = 0;
                Iterator<Order> iterator2 = allOrders.iterator();
                while (iterator2.hasNext()) {
                    Order order2 = iterator2.next();
                    Date date2 = order2.getLastUpdated();
                    date2 = dateOnly(date2);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(date2);
                    boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                    if (sameDay) {
                        count++;
                    }
                }
                String calendarString = "" + cal1.get(Calendar.DAY_OF_MONTH) + "/" + cal1.get(Calendar.MONTH) + "/"
                        + cal1.get(Calendar.YEAR) + "/" + count;
                toBeSorted.add(calendarString);
                counted.add(date1);
            }
        }
        insertionSort(toBeSorted);
        addDataFromSortedList(toBeSorted, dataSeries);
    }

    public static void insertionSort(ArrayList<String> array) {
        for (int i = 1; i < array.size(); i++) {
            String current = array.get(i);
            int j = i - 1;
            while(j >= 0 && dateLessThan(current, array.get(j))) {
                array.set(j + 1, array.get(j));
                j--;
            }
            array.set(j + 1, current);
        }
    }

    public static boolean dateLessThan(String date1, String date2) {
        String[] array1 = date1.split("/");
        String[] array2 = date2.split("/");
        if (Integer.parseInt(array1[2]) < Integer.parseInt(array2[2])) {
            return true;
        } else if (Integer.parseInt(array1[2]) == Integer.parseInt(array2[2])) {
            if (Integer.parseInt(array1[1]) < Integer.parseInt(array2[1])) {
                return true;
            } else if (Integer.parseInt(array1[1]) == Integer.parseInt(array2[1])) {
                if (Integer.parseInt(array1[0]) < Integer.parseInt(array2[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addDataFromSortedList(ArrayList<String> sortedArray, XYChart.Series dataSeries) {
        for (int i = 0; i < sortedArray.size(); i++) {
            String[] array = sortedArray.get(i).split("/");
            String calendarString = array[0] + "/" + array[1] + "/" + array[2];
            dataSeries.getData().add(new XYChart.Data<String,Number>(calendarString, Integer.parseInt(array[3])));
        }
    }

    public Date dateOnly(Date old) {
        long millisInDay = 60 * 60 * 24 * 1000;
        long currentTime = old.getTime();
        long dateOnly = (currentTime / millisInDay) * millisInDay + millisInDay;
        Date result = new Date(dateOnly);
        return result;
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
