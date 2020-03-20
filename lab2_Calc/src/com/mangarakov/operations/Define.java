package com.mangarakov.operations;

import com.mangarakov.calcException.LogicalExceptions.ArgumentFormatException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;

import java.util.Hashtable;
import java.util.LinkedList;

public class Define extends Operation {
    public Define(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) throws ArgumentNumberException, ArgumentFormatException {
        if (args.size() != 2) {
            throw new ArgumentNumberException("Wrong arguments number");
        }
        String name = args.get(0);
        String val = args.get(1);
        try {
            Double.parseDouble(val);
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException("Argument value should be double!");
        }
        try {
            Double.parseDouble(name);
            throw new ArgumentFormatException("Argument name should be string!");
        } catch (NumberFormatException e) {
            dict.put(name, Double.parseDouble(val));
        }

    }
}
