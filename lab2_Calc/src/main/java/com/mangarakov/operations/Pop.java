package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedList;

public class Pop extends Operation {
    public Pop(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private static final Logger logger = LoggerFactory.getLogger(Pop.class);

    @Override
    public void calculate(LinkedList<String> args) throws EmptyStackException {
        logger.info("Execute pop operation");
        pop();
    }
}
