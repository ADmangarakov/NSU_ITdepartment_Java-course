package com.mangarakov.tests.operations_tests;

import com.mangarakov.calcException.LogicalExceptions.ArgumentFormatException;
import com.mangarakov.calcException.LogicalExceptions.ArgumentNumberException;
import com.mangarakov.operations.Push;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class PushTest {
    HashMap<String, Double> dict = new HashMap<>();
    Stack<String> stack = new Stack<>();
    Hashtable<String, Object> context = new Hashtable<>();

    @Test
    void pushCorrectNumber() throws ArgumentFormatException, ArgumentNumberException {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Push push = new Push(context);
        LinkedList<String> args = new LinkedList<>();
        args.push("5");
        push.calculate(args);
        Stack<String> expectedResult = new Stack<>();
        expectedResult.push("5");
        Assert.assertEquals(expectedResult, stack);
    }

    @Test
    void pushCorrectDefVar() throws ArgumentFormatException, ArgumentNumberException {
        dict.put("name", (double) 16);
        context.put("dictionary", dict);
        context.put("stack", stack);
        Push push = new Push(context);

        LinkedList<String> args = new LinkedList<>();
        args.push("name");
        push.calculate(args);

        Stack<String> expectedResult = new Stack<>();
        expectedResult.push("name");
        Assert.assertEquals(expectedResult, stack);
    }

    @Test
    public void whenArgumentFormatExceptionThrown_thenAssertionSucceed_wrongArgFormat() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Push push = new Push(context);

        Exception exception = assertThrows(ArgumentFormatException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("name");
            push.calculate(args);
        });

        String expectedMessage = "This name wasn't define";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenArgumentNumberExceptionThrown_thenAssertionSucceed_tooManyArgs() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Push push = new Push(context);

        Exception exception = assertThrows(ArgumentNumberException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            args.push("5");
            args.push("6");
            push.calculate(args);
        });
        String expectedMessage = "Too many arguments";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenArgumentNumberExceptionThrown_thenAssertionSucceed_nothingToPush() {
        context.put("dictionary", dict);
        context.put("stack", stack);
        Push push = new Push(context);

        Exception exception = assertThrows(ArgumentNumberException.class, () -> {
            LinkedList<String> args = new LinkedList<>();
            push.calculate(args);
        });
        String expectedMessage = "Nothing to Push";
        String actualMessage = exception.getMessage();
        System.out.println("Then got message: " + actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }
}