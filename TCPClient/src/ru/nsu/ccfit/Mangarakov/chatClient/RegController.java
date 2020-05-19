package ru.nsu.ccfit.Mangarakov.chatClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegController {
    @FXML
    Button connectBtn;
    @FXML
    TextField inputName;
    @FXML
    Label statusLbl;
    @FXML
    private void click(ActionEvent event) {
        ChatClient.initConnection(inputName.getText());
    }

    public void setIllegalNameError() {
        statusLbl.setText("Name is engaged. Try again");
    }

    public void setServerNotAvailableError() {
        statusLbl.setText("Server is not available. Try later");
    }
}
