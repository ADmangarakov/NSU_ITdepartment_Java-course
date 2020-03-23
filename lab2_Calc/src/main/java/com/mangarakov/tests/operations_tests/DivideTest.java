package com.mangarakov.tests.operations_tests;

import com.mangarakov.calcException.ArithmeticExceptions.DivideByZeroException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.calcException.LogicalExceptions.EmptyStackException;
import com.mangarakov.operations.Divide;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DivideTest {
    HashMap<String, Double> dict = new HashMap<>();
    Stack<String> stack = new Stack<>();
    Hashtable<String, Object> context = new Hashtable<>();

    @Test
    void divNumCorrect() throws EmptyStackException, ArgumentNumberException, DivideByZeroException {
        double termF = 2;
        double termS = 5;
        stack.push(Double.toString(termF));
        stack.push(Double.toString(termS));
        context.put("dictionary", dict);
        context.put("stack", stack);
        Divide divider = new Divide(context);
        double expected_result = termF / termS;
        divider.calculate(new LinkedList<>());
        double real_result = Double.parseDouble(((Stack<String>) context.get("stack")).pop());

        Assert.assertEquals(expected_result, real_result, 0.001);
    }

    @Test
    public void whenEmptyStackExceptionThrown_thenAssertionSucceed() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Divide divider = new Divide(context);
        Exception exception = assertThrows(EmptyStackException.class, () -> divider.calculate(new LinkedList<>()));

        String expectedMessage = "Not enough elements in stack!";
        String actualMessage = exception.getMessage();
        System.out.println("Then got message: " + actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenDivideByZeroExceptionThrown_thenAssertionSucceed() {
        double termF = 5;
        double termS = 0;
        stack.push(Double.toString(termF));
        stack.push(Double.toString(termS));
        context.put("dictionary", dict);
        context.put("stack", stack);
        Divide divider = new Divide(context);
        Exception exception = assertThrows(DivideByZeroException.class, () -> divider.calculate(new LinkedList<>()));

        String expectedMessage = "Divide by zero!";
        String actualMessage = exception.getMessage();
        System.out.println("Then got message: " + actualMessage);
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
        Divide divider = new Divide(context);
        Exception exception = assertThrows(ArgumentNumberException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("5");
            divider.calculate(args);
        });

        String expectedMessage = "This command doesn't accept arguments";
        String actualMessage = exception.getMessage();
        System.out.println("Then got message: " + actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

}