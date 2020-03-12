package com.mangarakov.operations;

import java.util.Hashtable;
import java.util.LinkedList;

public class Subtract extends Operation {
    public Subtract(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private final static int argNumb = 2;

    @Override
    public void calculate(LinkedList<String> args) {
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            double result = termS - termF;
            push(Double.toString(result));
        } else {
            System.out.println("Not enough arguments");
        }
    }
}
