package seng202.team1.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditDataController {



    /**
     * Changes scene back to import with table
     */
    private void changeSceneToImport(javafx.event.ActionEvent event) throws IOException {
        Parent importParent = FXMLLoader.load(getClass().getResource("import.fxml"));
        Scene importScene = new Scene(importParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(importScene);
        window.show();
    }

    /**
     * goes back to the import table screen called by the back button
     */
    public void goBack(javafx.event.ActionEvent event) throws IOException {
        changeSceneToImport(event);
    }

}
