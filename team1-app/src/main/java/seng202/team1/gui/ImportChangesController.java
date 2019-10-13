package seng202.team1.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * controller for the import conflict resolution window.
 */
public class ImportChangesController {

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

