package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedList;

public class Print extends Operation {
    public Print(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private static final Logger logger = LoggerFactory.getLogger(Print.class);

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException {
        logger.info("Execute print operation");
        double firstOnStack = pop();
        System.out.println(firstOnStack);
        push(Double.toString(firstOnStack));
    }
}
