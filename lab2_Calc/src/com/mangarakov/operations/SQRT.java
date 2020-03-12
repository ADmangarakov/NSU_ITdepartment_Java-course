package com.mangarakov.operations;

import java.util.Hashtable;
import java.util.LinkedList;

import static java.lang.StrictMath.sqrt;

public class SQRT extends Operation {

    private final static int argNumb = 1;

    public SQRT(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) {
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double result = sqrt(termF);
            push(Double.toString(result));
        }
    }
}
