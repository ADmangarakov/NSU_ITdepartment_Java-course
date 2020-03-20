package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.ArgumentFormatException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;

import java.util.Hashtable;
import java.util.LinkedList;

public class Push extends Operation {
    public Push(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) throws ArgumentNumberException, ArgumentFormatException {
        if (args.size() == 0) {
            throw new ArgumentNumberException("Nothing to Push");
        }
        if (args.size() > 1){
            throw new ArgumentNumberException("Too many arguments");
        }
        for (String arg : args) {
            try {
                if (!dict.containsKey(arg)){
                    Double.parseDouble(arg);
                }
                push(arg);
            } catch (NumberFormatException e) {
                throw new ArgumentFormatException("This name wasn't define");
            }
        }
    }
}
