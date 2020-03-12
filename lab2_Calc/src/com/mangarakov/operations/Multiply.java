package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;

import java.util.Hashtable;
import java.util.LinkedList;

public class Multiply extends Operation {
    public Multiply(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private final static int argNumb = 2;

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException {
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            double result = termF * termS;
            push(Double.toString(result));
        }
        else {
            throw new EmptyStackException("Not enough elements on stack!");
        }
    }
}
