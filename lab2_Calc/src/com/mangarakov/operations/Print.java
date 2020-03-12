package com.mangarakov.operations;

import java.util.Hashtable;
import java.util.LinkedList;

public class Print extends Operation {
    public Print(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) {
        double firstOnStack = pop();
        System.out.println(firstOnStack);
        push(Double.toString(firstOnStack));
    }
}
