package com.mangarakov.operations;

import com.mangarakov.calcException.ArithmeticExceptions.DivideByZeroException;
import com.mangarakov.calcException.ArithmeticExceptions.NegativeNumberUnderRootException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentFormatException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;

import java.util.*;

abstract public class Operation {

    public Operation(Hashtable<String, Object> ctx) {
        stack = (Stack<String>) ctx.get("stack");
        dict = (HashMap<String, Double>) ctx.get("dictionary");
    }

    public abstract void calculate(LinkedList<String> args) throws
            ArgumentNumberException, ArgumentFormatException, EmptyStackException, DivideByZeroException, NegativeNumberUnderRootException;

    protected Stack<String> stack;
    protected HashMap<String, Double> dict;

    protected void push(String val) {
        stack.add(val);
    }

    protected double pop() throws NumberFormatException, EmptyStackException {

        String val;
        try {
            val = stack.pop();
        } catch (java.util.EmptyStackException e) {
            throw new EmptyStackException("Not enough element in stack");
        }
        if (dict.containsKey(val)) {
            return dict.get(val);
        } else {
            return Double.parseDouble(val);
        }
    }

    protected boolean isStackSizeNoLessThan(int size) {
        return stack.size() >= size;
    }
}


