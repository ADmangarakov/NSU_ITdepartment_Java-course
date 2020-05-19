package sample.threadPool;

import sample.details.Accessory;
import sample.details.Body;
import sample.details.Detail;
import sample.details.Engine;
import sample.Supplier;

import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Suppliers {
    private final Supplier<Engine> engSup;
    private final Supplier<Body> bodySup;
    private final Supplier<Accessory> accSup;

//    private ScheduledFuture<?> engFuture;
//    private ScheduledFuture<?> bodyFuture;
//    private ScheduledFuture<?> accFuture;

    //    private final ScheduledExecutorService service;
    private final ExecutorService service;
    private final Stack<Future<?>> supTask;
    private final int accSupNum;

    public Suppliers(Supplier<Engine> engSup, Supplier<Body> bodySup, Supplier<Accessory> accSup, int baseDelay, int accSupNum) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicLong threadIndex = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("Supplier" + threadIndex.getAndIncrement());
                return thread;
            }
        };
        this.engSup = engSup;
        this.bodySup = bodySup;
        this.accSup = accSup;
        this.accSupNum = accSupNum;
//        service = Executors.newSingleThreadScheduledExecutor();
        service = Executors.newFixedThreadPool(accSupNum + 2, threadFactory);
        supTask = new Stack<>();
    }

    public void startSupply() {
        supTask.push(service.submit(engSup));
        supTask.push(service.submit(bodySup));
        for (int i = 0; i < accSupNum; i++) {
            supTask.push(service.submit(accSup));
        }
//        engFuture = service.execute(engSup, 0, baseDelay, TimeUnit.MILLISECONDS);
//        bodyFuture = service.scheduleWithFixedDelay(bodySup, 0, baseDelay, TimeUnit.MILLISECONDS);
//        accFuture = service.scheduleWithFixedDelay(accSup, 0, baseDelay, TimeUnit.MILLISECONDS);
    }

    public void changeDelay(Class<? extends Detail> DType, long newDelay) {
        if (DType == Engine.class) {
            engSup.changeDelay(newDelay);
//            engFuture.cancel(true);
//            engFuture = service.scheduleWithFixedDelay(engSup, 0, newDelay, TimeUnit.MILLISECONDS);
            System.out.println("Current delay for Engine is " + newDelay);
        } else if (DType == Body.class) {
            bodySup.changeDelay(newDelay);
//            bodyFuture.cancel(true);
//            bodyFuture = service.scheduleWithFixedDelay(bodySup, 0, newDelay, TimeUnit.MILLISECONDS);
            System.out.println("Current delay for Body is " + newDelay);
        } else if (DType == Accessory.class) {
            accSup.changeDelay(newDelay);
//            accFuture.cancel(true);
//            accFuture = service.scheduleWithFixedDelay(accSup, 0, newDelay, TimeUnit.MILLISECONDS);
            System.out.println("Current delay for Accessory is " + newDelay);
        }
    }

    public void stop() {
        service.shutdownNow();
    }
}
