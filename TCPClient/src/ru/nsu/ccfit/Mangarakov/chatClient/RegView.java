package ru.nsu.ccfit.Mangarakov.chatClient;

import javafx.stage.Stage;

public class RegView {
    private static RegController regController;
    public static void setIllegalNameError() {
        regController.setIllegalNameError();
    }

    public static void setServerNotAvailableError() {
        regController.setServerNotAvailableError();
    }

    public static void init(RegController newRegController, Stage primaryStage) {
        regController = newRegController;
    }
}
