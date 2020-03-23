package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedList;

public class Subtract extends Operation {
    public Subtract(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private final static int argNumb = 2;
    private static final Logger logger = LoggerFactory.getLogger(Subtract.class);

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException, ArgumentNumberException {
        logger.info("Execute subtract");
        if (args.size() > 0) throw new ArgumentNumberException("This command doesn't accept arguments");
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            double result = termS - termF;
            push(Double.toString(result));
        } else {
            throw new EmptyStackException("Not enough elements on stack!");
        }
    }
}
