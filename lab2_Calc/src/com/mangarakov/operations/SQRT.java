package com.mangarakov.operations;

import com.mangarakov.calcException.ArithmeticExceptions.NegativeNumberUnderRootException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;

import java.util.Hashtable;
import java.util.LinkedList;

import static java.lang.StrictMath.sqrt;

public class SQRT extends Operation {

    private final static int argNumb = 1;

    public SQRT(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException, NegativeNumberUnderRootException, ArgumentNumberException {
        if (args.size() > 0) throw new ArgumentNumberException("This command doesn't accept arguments");
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            if (termF < 0) {
                push(Double.toString(termF));
                throw new NegativeNumberUnderRootException("Negative number under root!");
            }
            double result = sqrt(termF);
            push(Double.toString(result));
        }
        else {
            throw new EmptyStackException("Not enough elements on stack!");
        }
    }
}
