package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.ArgumentFormatException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedList;

public class Push extends Operation {
    public Push(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    private static final Logger logger = LoggerFactory.getLogger(Push.class);

    @Override
    public void calculate(LinkedList<String> args) throws ArgumentNumberException, ArgumentFormatException {
        logger.info("Execute push operation");
        if (args.size() == 0) {
            throw new ArgumentNumberException("Nothing to Push");
        }
        if (args.size() > 1) {
            throw new ArgumentNumberException("Too many arguments");
        }
        for (String arg : args) {
            try {
                if (!dict.containsKey(arg)) {
                    Double.parseDouble(arg);
                }
                push(arg);
            } catch (NumberFormatException e) {
                throw new ArgumentFormatException("This name wasn't define");
            }
        }
    }
}
