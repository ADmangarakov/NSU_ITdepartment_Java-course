package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedList;

public class Add extends Operation {
    public Add(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private final static int argNumb = 2;
    private static final Logger logger = LoggerFactory.getLogger(Add.class);

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException, ArgumentNumberException {
        logger.info("Execute add operation.");
        if (args.size() > 0) {
            throw new ArgumentNumberException("This command doesn't accept arguments");
        }
        if (isStackSizeNoLessThan(argNumb)) {
            double termF = pop();
            double termS = pop();
            double result = Double.sum(termF, termS);
            push(Double.toString(result));
        } else {
            throw new EmptyStackException("Not enough element in stack");
        }
    }
}
