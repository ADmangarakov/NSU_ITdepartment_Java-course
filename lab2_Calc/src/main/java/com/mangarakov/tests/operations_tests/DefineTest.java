package com.mangarakov.tests.operations_tests;

import com.mangarakov.calcException.LogicalExceptions.ArgumentFormatException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.operations.Define;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DefineTest {
    HashMap<String, Double> dict = new HashMap<>();
    Stack<String> stack = new Stack<>();
    Hashtable<String, Object> context = new Hashtable<>();

    @Test
    void defineCorrect() throws ArgumentFormatException, ArgumentNumberException {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Define definer = new Define(context);

        String name = "name";
        double val = 4;
        HashMap<String, Double> expectedResult = new HashMap<>();
        expectedResult.put(name, val);

        LinkedList<String> args = new LinkedList<>();
        args.push(Double.toString(val));
        args.push(name);
        definer.calculate(args);
        HashMap<String, Double> realResult = (HashMap<String, Double>) context.get("dictionary");
        assertEquals(expectedResult, realResult);
    }

    @Test
    public void whenArgumentNumberExceptionThrown_thenAssertionSucceed() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Define definer = new Define(context);
        Exception exception = assertThrows(ArgumentNumberException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("name");
            definer.calculate(args);
        });

        String expectedMessage = "Wrong arguments number";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenArgumentFormatExceptionThrown_thenAssertionSucceed_wrongNameFormat() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Define definer = new Define(context);
        Exception exception = assertThrows(ArgumentFormatException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("5");
            args.push("6");
            definer.calculate(args);
        });

        String expectedMessage = "Argument name should be string!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenArgumentFormatExceptionThrown_thenAssertionSucceed_wrongValueFormat() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Define definer = new Define(context);
        Exception exception = assertThrows(ArgumentFormatException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("name");
            args.push("notDouble");
            definer.calculate(args);
        });

        String expectedMessage = "Argument value should be double!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}