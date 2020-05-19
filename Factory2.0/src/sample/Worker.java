package sample;

import sample.details.Accessory;
import sample.details.Body;
import sample.details.Car;
import sample.details.Engine;

import java.util.logging.Logger;

public class Worker implements Runnable {
    private static final Logger log = Logger.getLogger(Warehouse.class.getName());
    private final Warehouse<Engine> engW;
    private final Warehouse<Body> bodyW;
    private final Warehouse<Accessory> accW;
    private final Warehouse<Car> carW;

    public Worker(Warehouse<Engine> engW, Warehouse<Body> bodyW,
                  Warehouse<Accessory> accW, Warehouse<Car> carW) {
        this.engW = engW;
        this.bodyW = bodyW;
        this.accW = accW;
        this.carW = carW;
    }


    @Override
    public void run() {
        log.fine(Thread.currentThread().getName() + " start");
        Car car = new Car(engW.get(), bodyW.get(), accW.get());
        carW.put(car);
        log.fine(Thread.currentThread().getName() + " finish");
    }
}
