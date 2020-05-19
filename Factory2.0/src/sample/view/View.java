package sample.view;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import sample.details.*;
import sample.Warehouse;
import sample.threadPool.Factory;

import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.Future;

public class View {
    private final Stack<Future<?>> tasks;

    private final Label engLabel;
    private final Label bodyLabel;
    private final Label accLabel;
    private final Label carLabel;

    private final ProgressBar engWBar;
    private final ProgressBar bodyWBar;
    private final ProgressBar accWBar;
    private final ProgressBar carWBar;

    private final Slider engContr;
    private final Slider bodyContr;
    private final Slider accContr;
    private final Slider dealerContr;

    private final Stage primaryStage;
    private Parent root;

    public View(Stage primaryStage, int engDelay, int bodyDelay, int accDelay, int dealerDelay ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 325));
        primaryStage.show();

        engLabel = (Label) root.lookup("#engCount");
        bodyLabel = (Label) root.lookup("#bodyCount");
        accLabel = (Label) root.lookup("#accCount");
        carLabel = (Label) root.lookup("#carCount");

        engWBar = (ProgressBar) root.lookup("#engW");
        bodyWBar = (ProgressBar) root.lookup("#bodyW");
        accWBar = (ProgressBar) root.lookup("#accW");
        carWBar = (ProgressBar) root.lookup("#carW");

        engContr = (Slider) root.lookup("#engContr");
        bodyContr = (Slider) root.lookup("#bodyContr");
        accContr = (Slider) root.lookup("#accContr");
        dealerContr = (Slider) root.lookup("#dealerContr");

        tasks = new Stack<>();
        initSliders(engDelay, bodyDelay, accDelay, dealerDelay);

        this.primaryStage = primaryStage;
    }

    private void initSliders(int engDelay, int bodyDelay, int accDelay, int dealerDelay) {
        engContr.setValue(engDelay);
        bodyContr.setValue(bodyDelay);
        accContr.setValue(accDelay);
        dealerContr.setValue(dealerDelay);

        engContr.valueProperty().addListener((observable, oldValue, newValue) -> {
            Factory.changeDelay(Engine.class, newValue.intValue());
        });
        bodyContr.valueProperty().addListener((observable, oldValue, newValue) -> {
            Factory.changeDelay(Body.class, newValue.intValue());
        });
        accContr.valueProperty().addListener((observable, oldValue, newValue) -> {
            Factory.changeDelay(Accessory.class, newValue.intValue());
        });
        dealerContr.valueProperty().addListener((observable, oldValue, newValue) -> {
            Factory.changeDelay(Car.class, newValue.intValue());
        });


    }

    /*
     *
     * Create 6 thread for update view
     *
     * */
    public void startView(Warehouse<Engine> engW, Warehouse<Body> bodyW, Warehouse<Accessory> accW, Warehouse<Car> carW) {
        updL(engW, Engine.class);
        updL(bodyW, Body.class);
        updL(accW, Accessory.class);
        updL(carW, Car.class);

        updPB(engW, Engine.class);
        updPB(bodyW, Body.class);
        updPB(accW, Accessory.class);
        updPB(carW, Car.class);


        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Exit!");
            Factory.stop();
        });

        primaryStage.show();

    }


    private void updPB(Warehouse<? extends Detail> warehouse, Class<?> DType) {
        int maxSize = warehouse.getMaxSize();
        Task updWData = new Task<Void>() {
            @Override
            public Void call() {
                while (!isCancelled()) {
                    updateProgress(warehouse.getCountDetail(), maxSize);
                }
                return null;
            }
        };
        if (DType == Engine.class) {
            engWBar.progressProperty().bind(updWData.progressProperty());
        } else if (DType == Body.class) {
            bodyWBar.progressProperty().bind(updWData.progressProperty());
        } else if (DType == Accessory.class) {
            accWBar.progressProperty().bind(updWData.progressProperty());
        } else if(DType == Car.class) {
            carWBar.progressProperty().bind(updWData.progressProperty());
        }

        Thread updTh = new Thread(updWData);
        updTh.setDaemon(true);
        updTh.start();
    }

    private void updL(Warehouse<? extends Detail> warehouse, Class<?> DType) {
        Task updSupData = new Task<Void>() {
            @Override
            public Void call() {
                while (!isCancelled()) {
                    updateMessage(String.valueOf(warehouse.getTotalAmount()));
                }
                return null;
            }
        };
        if (DType == Engine.class) {
            engLabel.textProperty().bind(updSupData.messageProperty());
        } else if (DType == Body.class) {
            bodyLabel.textProperty().bind(updSupData.messageProperty());
        } else if (DType == Accessory.class) {
            accLabel.textProperty().bind(updSupData.messageProperty());
        } else if(DType == Car.class) {
            carLabel.textProperty().bind(updSupData.messageProperty());
        }
        Thread updTh = new Thread(updSupData);
        updTh.setDaemon(true);
        updTh.start();
    }
}
