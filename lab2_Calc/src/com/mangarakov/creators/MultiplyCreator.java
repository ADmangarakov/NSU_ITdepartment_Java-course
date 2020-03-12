package com.mangarakov.creators;

import com.mangarakov.operations.Multiply;
import com.mangarakov.operations.Operation;

import javax.naming.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class MultiplyCreator extends OperationCreator {
    @Override
    public Operation createOperation(Hashtable<String, Object> ctx) {
        try {
            Constructor ctr = Multiply.class.getDeclaredConstructor(Hashtable.class);
            return (Multiply) ctr.newInstance(ctx);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
