package com.mangarakov.operations;

import com.mangarakov.calcException.ArithmeticExceptions.DivideByZeroException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;

import java.util.Hashtable;
import java.util.LinkedList;

public class Divide extends Operation {
    private final static int argNumb = 2;

    public Divide(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException, ArgumentNumberException, DivideByZeroException {
        if (args.size() > 0) throw new ArgumentNumberException("This command doesn't accept arguments");
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            if (termF == 0) {
                push(Double.toString(termS));
                push(Double.toString(termF));
                throw new DivideByZeroException("Divide by zero!");
            }
            double result = termS / termF;
            push(Double.toString(result));
        } else {
            throw new EmptyStackException("Not enough elements in stack!");
        }
    }
}
