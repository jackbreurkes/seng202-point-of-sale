package seng202.team1.GUI;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import seng202.team1.data.UploadHandler;

import java.io.File;

public class ImportController {

    @FXML
    private Text statusText;

    /*
    Opens file chooser and then imports file if a file of the correct type is selected
    @ return null
     */
    public void importFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileExtension = "";
            int i = fileName.lastIndexOf('.');
            if (i >= 0) { fileExtension = fileName.substring(i+1); }
            if (fileExtension == "xml") {
                statusText.setText("File import started.");
                // call to import method from xml import class

                
                UploadHandler handler = new UploadHandler();
                // to upload filename if it's food item
                handler.uploadFoodItems(fileName);



                //
            } else {
                statusText.setText("Incorrect file type.");
            }
        } else {
            statusText.setText("No file selected.");
        }
    }
}
