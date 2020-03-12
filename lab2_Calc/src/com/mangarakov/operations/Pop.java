package com.mangarakov.operations;

import java.util.Hashtable;
import java.util.LinkedList;

public class Pop extends Operation {
    public Pop(Hashtable<String, Object> ctx) {
        super(ctx);
    }

    @Override
    public void calculate(LinkedList<String> args) {
        pop();
    }
}
