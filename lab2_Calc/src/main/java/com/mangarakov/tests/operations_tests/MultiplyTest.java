package com.mangarakov.tests.operations_tests;

import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import com.mangarakov.operations.Add;
import com.mangarakov.operations.Multiply;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyTest {
    HashMap<String, Double> dict = new HashMap<>();
    Stack<String> stack = new Stack<>();
    Hashtable<String, Object> context = new Hashtable<>();

    @Test
    void sumNumCorrect() throws EmptyStackException, ArgumentNumberException {
        double termF = 2;
        double termS = 5;
        stack.push(Double.toString(termF));
        stack.push(Double.toString(termS));
        context.put("dictionary", dict);
        context.put("stack", stack);
        Multiply multiply = new Multiply(context);
        double expected_result = termF * termS;
        multiply.calculate(new LinkedList<>());
        double real_result = Double.parseDouble(((Stack<String>) context.get("stack")).pop());

        Assert.assertEquals(expected_result, real_result, 0.001);
    }

    @Test
    public void whenEmptyStackExceptionThrown_thenAssertionSucceed() {
        stack.push(Double.toString(5));
        context.put("dictionary", dict);
        context.put("stack", stack);

        Multiply multiply = new Multiply(context);
        Exception exception = assertThrows(EmptyStackException.class, () -> multiply.calculate(new LinkedList<>()));

        String expectedMessage = "Not enough elements on stack!";
        String actualMessage = exception.getMessage();
        System.out.println("Then got message: " + actualMessage);
        System.out.println("Expected message: " + expectedMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenArgumentNumberExceptionThrown_thenAssertionSucceed() {
        double termF = 2;
        double termS = 5;
        stack.push(Double.toString(termF));
        stack.push(Double.toString(termS));
        context.put("dictionary", dict);
        context.put("stack", stack);
        Add adder = new Add(context);
        Exception exception = assertThrows(ArgumentNumberException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("5");
            adder.calculate(args);
        });

        String expectedMessage = "This command doesn't accept arguments";
        String actualMessage = exception.getMessage();
        System.out.println("Then got message: " + actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }
}