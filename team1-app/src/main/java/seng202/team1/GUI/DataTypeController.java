package seng202.team1.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.team1.data.MemoryStorage;
import seng202.team1.data.UploadHandler;

import java.io.File;
import java.io.IOException;

public class DataTypeController {

    @FXML
    private ComboBox dataTypeComboBox;

    @FXML
    private Label dataTypeStatus;

    /**
     * Changes scene back to import with table
     */
    private void changeSceneToImport(javafx.event.ActionEvent event) throws IOException {
        Parent importParent = FXMLLoader.load(getClass().getResource("import.fxml"));
        Scene importScene = new Scene(importParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("ROSEMARY | Import Screen");

        window.setScene(importScene);
        window.show();
    }

    /**
     * goes back to the import table screen called by the back button
     */
    public void goBack(javafx.event.ActionEvent event) throws IOException {
        changeSceneToImport(event);
    }

    /**
     * When this methods is called, it will change the scene to datatype controller view
     */
    public void changeSceneToOrder(javafx.event.ActionEvent event) throws IOException
    {
        Parent editDataParent = FXMLLoader.load(getClass().getResource("order.fxml"));
        Scene editDataScene = new Scene(editDataParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //This line sets the screen title
        window.setTitle("ROSEMARY | Order Screen");

        window.setScene(editDataScene);
        window.show();
    }


    /**
     * Opens file chooser and then imports file if a file of the correct type is selected
     */
    public void importFile(javafx.event.ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileExtension = "";
            int i = fileName.lastIndexOf('.');
            if (i >= 0) {
                fileExtension = fileName.substring(i + 1);
            }
            if (fileExtension.equals("xml")) {
                //ImportController.setStatusText("File import started.");
                if (dataTypeComboBox.getValue().toString().equals("Food Items")) {
                    UploadHandler.uploadFoodItems(selectedFile.getPath());
                    System.out.println(MemoryStorage.getInstance().getAllFoodItems());
                    //ImportController.setStatusText("File import started.");
                } else if (dataTypeComboBox.getValue().toString().equals("Suppliers")) {
                    UploadHandler.uploadSuppliers(selectedFile.getPath());
                    //ImportController.setStatusText("File import started.");
                } else {
                    dataTypeStatus.setText("No data type selected.");
                }
                changeSceneToImport(event);
            } else {
                dataTypeStatus.setText("Incorrect file type.");
            }
        } else {
            dataTypeStatus.setText("No file selected.");
        }
    }

    /**
     * Runs on opening of scene sets up combobox values
     */
    public void initialize() {
        dataTypeComboBox.getItems().addAll("Suppliers", "Food Items");
    }
}
