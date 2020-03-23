package com.mangarakov.creators;

import com.mangarakov.operations.Operation;
import com.mangarakov.operations.Print;

import javax.naming.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class PrintCreator extends OperationCreator {
    @Override
    public Operation createOperation(Hashtable<String, Object> ctx) {
        try {
            Constructor ctr = Print.class.getDeclaredConstructor(Hashtable.class);
            return (Print) ctr.newInstance(ctx);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
