package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import java.util.Hashtable;
import java.util.LinkedList;

 public class Add extends Operation {
    public Add(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private final static int argNumb = 2;

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException, ArgumentNumberException {
        if (args.size() > 0) {
            throw new ArgumentNumberException("This command doesn't accept arguments");
        }
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            double result = Double.sum(termF, termS);
            push(Double.toString(result));
        }
        else {
            throw new EmptyStackException("Not enough element in stack");
        }
    }
}
