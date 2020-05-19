package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;

import java.awt.event.ActionEvent;

public class Controller {
    @FXML
    private ScrollBar engScroll;
    @FXML
    private ScrollBar bodyScroll;
    @FXML
    private ScrollBar accScroll;
    @FXML
    private ScrollBar carScroll;
    @FXML
    private Label engCount;
    @FXML
    private Label bodyCount;
    @FXML
    private Label accCount;
    @FXML
    private Label carCount;
    @FXML
    private ProgressBar carW;
    @FXML
    private ProgressBar engW;
    @FXML
    private ProgressBar bodyW;
    @FXML
    private ProgressBar accW;

    @FXML
    private void changeSpeed(ActionEvent event) {

    }
}
