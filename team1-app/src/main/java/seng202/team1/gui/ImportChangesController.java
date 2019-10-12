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

    private boolean overwrite;
    private ImportController importController;

    private static final String DESCRIPTION =
            "Uploaded file contains food item that is " +
            "already present in database with different values";

    public void accept() {
        importController.setOverwrite(true);
        closeWindow(acceptButton);
    }

    public void ignore() {
        importController.setOverwrite(false);
        closeWindow(ignoreButton);
    }


    /**
     * runs automatically when the window is created
     */
    public void initialize(ImportController importController) {
        this.importController = importController;
        descriptionLabel.setText(DESCRIPTION);

    }

    private void closeWindow(Node node) {
        Stage stage  = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public boolean getOverwrite() {
        return overwrite;
    }

    public void setImportController(ImportController importController) {
        this.importController = importController;
    }
}

