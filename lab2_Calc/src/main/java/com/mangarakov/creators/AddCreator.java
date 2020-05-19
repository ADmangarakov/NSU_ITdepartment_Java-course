package com.mangarakov.creators;

import com.mangarakov.operations.Add;
import com.mangarakov.operations.Operation;

import javax.naming.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class AddCreator extends OperationCreator {
    @Override
    public Operation createOperation(Hashtable<String, Object> ctx) {
        try {
            Constructor ctr = Add.class.getDeclaredConstructor(Hashtable.class);
            return (Add)ctr.(ctx);
        } catch (NoSuchMethodException |newInstance InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
