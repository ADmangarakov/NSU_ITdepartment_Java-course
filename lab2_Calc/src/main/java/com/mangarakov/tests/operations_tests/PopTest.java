package com.mangarakov.tests.operations_tests;

import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import com.mangarakov.operations.Pop;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class PopTest {
    HashMap<String, Double> dict = new HashMap<>();
    Stack<String> stack = new Stack<>();
    Hashtable<String, Object> context = new Hashtable<>();

    @Test
    void popCorrect() throws EmptyStackException {
        double termF = 2;
        stack.push(Double.toString(termF));
        context.put("dictionary", dict);
        context.put("stack", stack);
        Pop popper = new Pop(context);
        popper.calculate(null);
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void whenEmptyStackExceptionThrown_thenAssertionSucceed() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Pop popper = new Pop(context);
        Exception exception = assertThrows(EmptyStackException.class, () -> popper.calculate(null));

        String expectedMessage = "Not enough element in stack";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}