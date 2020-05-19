package sample.threadPool;

import sample.details.Accessory;
import sample.details.Body;
import sample.details.Car;
import sample.details.Engine;
import sample.Warehouse;
import sample.Worker;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class AssemblyShop {
    private final ExecutorService service;
    private final Worker worker;
    private final Stack<Future<?>> workersTask;

    public AssemblyShop(int nThreads, Warehouse<Engine> engW, Warehouse<Body> bodyW, Warehouse<Accessory> accW, Warehouse<Car> carW) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicLong threadIndex = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("Worker" + threadIndex.getAndIncrement());
                return thread;
            }
        };
        service = Executors.newFixedThreadPool(nThreads, threadFactory);
        worker = new Worker(engW, bodyW, accW, carW);
        workersTask = new Stack<>();
    }

    public void start() {
        service.submit(worker);
//        workersTask.push(service.submit(worker));
    }

    public void stop() {
        service.shutdownNow();
//        while (!workersTask.isEmpty()) {
//            workersTask.pop().cancel(true);
//        }
    }

}
