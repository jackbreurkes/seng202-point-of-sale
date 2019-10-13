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


    /**
     * Runs on opening of the window
     */
    public void initialize() {
        xComboBox.getItems().addAll("Date", "Time", "Day of week");
        yComboBox.getItems().addAll("Orders");
        xComboBox.setValue("Date");
        yComboBox.setValue("Orders");
    }

    /**
     * plots a graph based on the options chosen from the combo boxes
     */
    public void plotGraph() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(String.valueOf(xComboBox.getValue()));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(String.valueOf(yComboBox.getValue()));

        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis,yAxis);

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName(String.valueOf(yComboBox.getValue()) + " against " + String.valueOf(xComboBox.getValue()));
        if (xComboBox.getValue() == "Date") {
            addDateDataToSeries(dataSeries);
        } else if (xComboBox.getValue() == "Time") {
            addTimeDataToSeries(dataSeries);
        } else if (xComboBox.getValue() == "Day of week") {
            addDayDataToSeries(dataSeries);
        }

        lineChart.getData().add(dataSeries);

        graphVbox.getChildren().removeAll(graphVbox.getChildren());
        graphVbox.getChildren().add(lineChart);

    }

    /**
     * Takes a data series ad adds data to plot a graph of orders on each day whe there was at least oe order
     * @param dataSeries the dataseries initialized in the plot graph method
     */
    public void addDateDataToSeries(XYChart.Series dataSeries) {
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

    /**
     * Takes a data series and populates it with data for a graph showing how may orders were at each hour of the day
     * @param dataSeries the dataseries initialised in the plot graph method
     */
    public void addTimeDataToSeries(XYChart.Series dataSeries) {
        JDBCStorage memory = JDBCStorage.getInstance();
        Set<Order> allOrders = memory.getAllOrders();

        int[] ordersEachHour = new int[24];
        Iterator<Order> iterator = allOrders.iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();
            Date date = order.getLastUpdated();
            String dateString = date.toString();
            int hour = Integer.parseInt(dateString.substring(11, 13)) + 13;
            if (hour >= 24) {
                hour = hour - 24;
            }
            ordersEachHour[hour] += 1;
        }
        for (int i = 0; i < 24; i++) {
            dataSeries.getData().add(new XYChart.Data("" + i, ordersEachHour[i]));
        }
    }

    /**
     * Takes a data series and populates it with data for a graph showing how may orders have been on each day of the week
     *
     * @param dataSeries the dataseries initialised in the plot graph method
     */
    public void addDayDataToSeries(XYChart.Series dataSeries) {
        JDBCStorage memory = JDBCStorage.getInstance();
        Set<Order> allOrders = memory.getAllOrders();

        int[] ordersEachDay = new int[7];
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        Iterator<Order> iterator = allOrders.iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();
            Date date = order.getLastUpdated();
            String dateString = date.toString();
            String day = dateString.substring(0, 3);
            for (int i = 0; i < 7; i++) {
                if (day.equals(days[i])) {
                    ordersEachDay[i] += 1;
                    break;
                }
            }
        }
        for (int i = 0; i < 7; i++) {
            dataSeries.getData().add(new XYChart.Data(days[i], ordersEachDay[i]));
        }
    }

    /**
     * insertion sort on an array of datestrings
     * @param array and array of date strings to be sorted
     */
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

    /**
     * return true if date1 is before date2
     * @param date1
     * @param date2
     * @return boolean true if date1 is before date2
     */
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

    /**
     * takes a sorted array of date strings and adds it to the dataseries provided
     * @param sortedArray a sorted array of date strings returned by the insertion sort method
     * @param dataSeries the dataseries initialized in plot graph
     */
    public static void addDataFromSortedList(ArrayList<String> sortedArray, XYChart.Series dataSeries) {
        for (int i = 0; i < sortedArray.size(); i++) {
            String[] array = sortedArray.get(i).split("/");
            String calendarString = array[0] + "/" + array[1] + "/" + array[2];
            dataSeries.getData().add(new XYChart.Data<String,Number>(calendarString, Integer.parseInt(array[3])));
        }
    }

    /**
     * takes a date and sets the time to be 0 for testing equality of dates.
     * @param old the old date value before neutralising time
     * @return result the new date with time neutralised for testing equality of two dates on the same day
     */
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
    public void changeSceneToFoodItemStorage(javafx.event.ActionEvent event) throws IOException
    {
        SceneController sceneChanger = new SceneController();
        sceneChanger.changeScene(event, "foodItemStorage.fxml", "ROSEMARY | Food Item Storage");
    }
}
