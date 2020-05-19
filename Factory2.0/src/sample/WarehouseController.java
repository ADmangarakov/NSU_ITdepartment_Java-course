package sample;

import sample.details.Car;
import sample.threadPool.AssemblyShop;
import java.util.logging.Logger;

public class WarehouseController implements Runnable {
    private static final Logger log = Logger.getLogger(Warehouse.class.getName());
    private final Warehouse<Car> carWarehouse;
    private final AssemblyShop assemblyShop;

    public WarehouseController(Warehouse<Car> carWarehouse, AssemblyShop assemblyShop) {
        this.carWarehouse = carWarehouse;
        this.assemblyShop = assemblyShop;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            carWarehouse.getRequest();
            assemblyShop.start();
        }
    }
}
