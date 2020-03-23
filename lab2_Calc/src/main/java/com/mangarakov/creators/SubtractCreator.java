package com.mangarakov.creators;

import com.mangarakov.operations.Operation;
import com.mangarakov.operations.Subtract;

import javax.naming.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class SubtractCreator extends OperationCreator {
    @Override
    public Operation createOperation(Hashtable<String, Object> ctx) {
        try {
            Constructor ctr = Subtract.class.getDeclaredConstructor(Hashtable.class);
            return (Subtract) ctr.newInstance(ctx);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
