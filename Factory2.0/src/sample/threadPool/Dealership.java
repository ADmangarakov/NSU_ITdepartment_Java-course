package sample.threadPool;

import sample.Dealer;
import sample.details.Car;
import sample.Warehouse;

import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Dealership {
//    private final ScheduledExecutorService service;
    private final ExecutorService service;
    private final Dealer dealer;
    private final Stack<Future<?>> dealersTask;
    private final int nThread;
    private long delay;


    public Dealership(int nThreads, Warehouse<Car> carW, long baseDelay, boolean logging) {
//        service = Executors.newSingleThreadScheduledExecutor();
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicLong threadIndex = new AtomicLong(0);
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("Dealer" + threadIndex.getAndIncrement());
                return thread;
            }
        };
        service = Executors.newFixedThreadPool(nThreads, threadFactory);
        dealer = new Dealer(carW, logging);
        dealersTask = new Stack<>();
        delay = baseDelay;
        this.nThread = nThreads;
    }

    public void start() {
        for (int i = 0; i < nThread; i++)
            dealersTask.push(service.submit(dealer));
//            dealersTask[i] = service.scheduleWithFixedDelay(dealer, 0, delay, TimeUnit.MILLISECONDS);
    }

    public void changeDelay(long delay) {
//        for (int i = 0; i < nThread; i++) {
//            dealersTask[i].cancel(true);
//            dealersTask[i] = service.scheduleWithFixedDelay(dealer, 0, delay, TimeUnit.MILLISECONDS);
//        }
        dealer.changeDelay(delay);
        System.out.println("Delay for Dealers is " + delay);
        this.delay = delay;
    }

    public void stop() {
        service.shutdownNow();
    }
}
