package seng202.team1.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    /**
     * Changes scene to the given fxml file name and gives title given. To be called when user
     * input specifies a scene change
     * @param event action event that called the method from java fx scene (will just be called event in practice)
     * @param fxmlFile string name of the file to create new scene from
     * @param title string title of the new scene to be displayed
     * @throws IOException
     */
    public void changeScene(javafx.event.ActionEvent event, String fxmlFile, String title) throws IOException {
        Parent newParent = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene newScene = new Scene(newParent);
        newScene.getStylesheets().add("stylesheets/default.css");

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //This line sets the screen title
        window.setTitle(title);

        window.setScene(newScene);
        window.setFullScreenExitHint("");
        window.setFullScreen(true);
        window.show();
    }
}
