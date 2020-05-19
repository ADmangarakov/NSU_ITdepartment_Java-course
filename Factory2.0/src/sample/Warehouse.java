package sample;


import sample.details.Detail;

import java.util.Stack;
import java.util.logging.Logger;

public class Warehouse<DType extends Detail> {
    private static final Logger log = Logger.getLogger(Warehouse.class.getName());
    private final Class<DType> type;

    private final Stack<DType> detailStack;
    private int countDetail;
    private int totalAmount;
    private final int maxSize;
    private boolean isEmpty;

    public Warehouse(int maxSize, Class<DType> type) {
        detailStack = new Stack<>();
        countDetail = 0;
        totalAmount = 0;
        this.maxSize = maxSize;
        this.type = type;
        isEmpty = true;
    }

    private String getDetailType() {
        return this.type.getName();
    }

    public synchronized int getTotalAmount() {
        return totalAmount;
    }

    public synchronized int getCountDetail() {
        return countDetail;
    }

    public synchronized int getMaxSize() {
        return maxSize;
    }


    public synchronized void put(DType detail) {
        while (countDetail >= maxSize) {
            try {
//                log.info(getDetailType() + " warehouse is full");
                wait();
            } catch (InterruptedException e) {
                log.info(e.getLocalizedMessage());
            }
        }
        countDetail++;
        totalAmount++;

//        log.info(getDetailType() + " added. Current count is " + countDetail);
        detailStack.push(detail);
        notify();
    }

    public synchronized DType get() {
        while (countDetail < 1) {
            try {
                isEmpty = true;
                wait();
            } catch (InterruptedException e) {
                log.info(e.getLocalizedMessage());
            }
        }
        isEmpty = false;
        countDetail--;

        DType detail = detailStack.pop();
        notify();
        return detail;
    }

    public synchronized void getRequest() {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
    }
}
