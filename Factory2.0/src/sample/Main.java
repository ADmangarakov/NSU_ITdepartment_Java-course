package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.threadPool.Factory;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        InputStream conf = Main.class.getResourceAsStream("/factory.properties");
        Factory.initFactory(conf, primaryStage);
        Factory.startFactory();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
