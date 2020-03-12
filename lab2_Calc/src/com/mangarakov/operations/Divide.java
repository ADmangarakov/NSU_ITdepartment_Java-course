package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;

import java.util.Hashtable;
import java.util.LinkedList;

public class Divide extends Operation {
    private final static int argNumb = 2;

    public Divide(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException {
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            double result = termS / termF;
            push(Double.toString(result));
        } else {
            throw new EmptyStackException("Not enough elements in stack!");
        }
    }
}
