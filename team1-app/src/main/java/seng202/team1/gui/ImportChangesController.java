package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ImportChangesController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button acceptButton;

    @FXML
    private Button ignoreButton;

    /**
     * A FoodItemStorageController.
     * Note that despite being a FoodItemStorageController object,
     * variable is called importController as this particular functionality
     * imports data into the storage database, and therefore it is
     * intuitively named an importController.
     */
    private FoodItemStorageController importController;

    /**
     * Called when the accept button is clicked.
     * Sets the overwrite variable in the FoodItemStorageController variable
     * importController to true, and then closes the pop up window.
     */
    public void accept() {
        importController.setOverwrite(true);
        closeWindow(acceptButton);
    }

    /**
     * Called when the ignore button is clicked.
     * Sets the overwrite variable in the FoodItemStorageController variable
     * importController to false, and then closes the pop up window.
     */
    public void ignore() {
        importController.setOverwrite(false);
        closeWindow(ignoreButton);
    }


    /**
     * Runs automatically when the window is created
     */

    /**
     * Runs when the pop upwindow is created. Takes a FoodItemStorageController
     * for usage in setting the variable 'overwrite' in the aforementioned class.
     * @param importController a FoodItemStorageController
     */
    public void initialize(FoodItemStorageController importController) {
        this.importController = importController;
    }

    /**
     * Closes the pop up window.
     * @param node a node that represents the window
     */
    private void closeWindow(Node node) {
        Stage stage  = (Stage) node.getScene().getWindow();
        stage.close();
    }

    /**
     * Called from FoodItemStorageController to set the variable importController
     * to the FoodItemStorageController itself.
     * @param importController a FoodItemStorageController that handles import of data
     */
    public void setImportController(FoodItemStorageController importController) {
        this.importController = importController;
    }
}

