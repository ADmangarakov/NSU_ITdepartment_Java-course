package com.mangarakov.creators;

import com.mangarakov.operations.Operation;

import java.util.Hashtable;

public abstract class OperationCreator {

    public abstract Operation createOperation(Hashtable<String, Object> ctx);
}
