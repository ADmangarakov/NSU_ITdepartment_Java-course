package sample.threadPool;

import javafx.stage.Stage;
import sample.details.*;
import sample.Supplier;
import sample.view.View;
import sample.Warehouse;
import sample.WarehouseController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Factory {
    private static WarehouseController warehouseController;

    private static Dealership dealership;
    private static AssemblyShop assemblyShop;
    private static Suppliers suppliers;

    private static Warehouse<Engine> engW;
    private static Warehouse<Body> bodyW;
    private static Warehouse<Accessory> accW;
    private static Warehouse<Car> carW;

    private static Supplier<Engine> engSup;
    private static Supplier<Body> bodySup;
    private static Supplier<Accessory> accSup;

    private static View view;
    private static Integer wEngSize, wBodySize, wAccSize, wCarSize, accSupNum, workNum, dealersNum;
    private static Boolean logging;

    public static void initFactory(InputStream conf, Stage primaryStage) throws IOException {
        getConf(conf);

        engW = new Warehouse<>(wEngSize, Engine.class);
        bodyW = new Warehouse<>(wBodySize, Body.class);
        accW = new Warehouse<>(wAccSize, Accessory.class);
        carW = new Warehouse<>(wCarSize, Car.class);

        assemblyShop = new AssemblyShop(workNum, engW, bodyW, accW, carW);
        dealership = new Dealership(dealersNum, carW, 500, logging);
        warehouseController = new WarehouseController(carW, assemblyShop);

        engSup = new Supplier<>(engW, Engine.class);
        bodySup = new Supplier<>(bodyW, Body.class);
        accSup = new Supplier<>(accW, Accessory.class);
        suppliers = new Suppliers(engSup, bodySup, accSup, 3000, accSupNum);

        view = new View(primaryStage, 2500, 2500, 2500, 500);
    }

    private static void getConf(InputStream conf) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conf));
        try {
            while (reader.ready()) {
                String[] param = reader.readLine().split("=");
                if (param[0].equals("LogSale")){
                    logging = Boolean.parseBoolean( param[1]);
                    continue;
                }
                int paramValue = Integer.parseInt(param[1]);
                switch (param[0]) {
                    case "StorageBodySize":
                        wBodySize = paramValue;
                        break;
                    case "StorageMotorSize":
                        wEngSize = paramValue;
                        break;
                    case "StorageAccessorySize":
                        wAccSize = paramValue;
                        break;
                    case "StorageAutoSize":
                        wCarSize = paramValue;
                        break;
                    case "AccessorySuppliers":
                        accSupNum = paramValue;
                        break;
                    case "Workers":
                        workNum = paramValue;
                        break;
                    case "Dealers":
                        dealersNum = paramValue;
                        break;
                    default:
                        System.err.println("Wrong param");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void startFactory() {
        assemblyShop.start();
        dealership.start();
        suppliers.startSupply();
        new Thread(warehouseController).start();
        view.startView(engW, bodyW, accW, carW);
    }

    public static void changeDelay(Class<? extends Detail> DType, long newDelay) {
        if (DType == Car.class) {
            dealership.changeDelay(newDelay);
        }
        suppliers.changeDelay(DType, newDelay);
    }

    public static void stop() {
        assemblyShop.stop();
        dealership.stop();
        suppliers.stop();
        System.exit(0);
    }
}
