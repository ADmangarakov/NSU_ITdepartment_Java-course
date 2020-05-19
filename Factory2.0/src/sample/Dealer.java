package sample;

import sample.details.Car;

import java.util.logging.Logger;

public class Dealer implements Runnable {
    private static final Logger log = Logger.getLogger(Warehouse.class.getName());
    private final Warehouse<Car> carWarehouse;
    private long delay;
    private final boolean logging;

    public Dealer(Warehouse<Car> carWarehouse, boolean logging) {
        this.carWarehouse = carWarehouse;
        this.logging = logging;
        delay = 2500;
    }

    public void changeDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        log.fine(Thread.currentThread().getName() + " start");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Car car = carWarehouse.get();
                if (logging) {
                    log.info(Runtime.getRuntime() + ": Dealer " + Thread.currentThread().getName() + ": Auto" + car.getId());
                    System.out.println(Runtime.getRuntime() + ": Dealer " + Thread.currentThread().getName() + ": Auto" + car.getId());
                }
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            log.fine(Thread.currentThread().getName() + " finish");
        }
    }
}
