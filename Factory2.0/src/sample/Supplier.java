package sample;

import sample.details.Detail;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class Supplier<DType extends Detail> implements Runnable {
    private static final Logger log = Logger.getLogger(Warehouse.class.getName());
    private final Class<DType> type;
    private final Warehouse<DType> warehouse;
    private long delay;

    public Supplier(Warehouse<DType> warehouse, Class<DType> type) {
        this.type = type;
        this.warehouse = warehouse;
        delay = 1000;
    }

    public void changeDelay(long delay) {
        this.delay = delay;
    }

    public void run() {
        log.fine(Thread.currentThread().getName() + " start");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                DType newItem = type.getDeclaredConstructor().newInstance();
                warehouse.put(newItem);
                Thread.sleep(delay);
                log.fine(getDetailType() + " supplier produced detail");
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InterruptedException e) {
            log.fine(Thread.currentThread().getName() + " finish");
        }
    }

    private String getDetailType() {
        return this.type.getName();
    }

}
