package com.mangarakov;

import com.mangarakov.creators.*;
import com.mangarakov.operations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.logging.LogManager;

public class Calculator {

    private static HashMap<String, Double> dict = new HashMap<>();
    private static Stack<String> stack = new Stack<>();
    private static Hashtable<String, Object> context = new Hashtable<>();
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    public static void main(String[] args) throws IOException {
        try {
            LogManager.getLogManager().readConfiguration(
                    Calculator.class.getResourceAsStream("/log4j.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        context.put("dictionary", dict);
        context.put("stack", stack);
        InputStream in = System.in;
        if (args.length > 0) {
            try {
                in = new FileInputStream(args[0]);
            } catch (FileNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        calculate(parseCommand(in));
    }

    private static ArrayList<String[]> parseCommand(InputStream in) throws IOException {
        logger.info("Parsing string");
        ArrayList<String[]> parsedCommands = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String s;
        while ((s = br.readLine()) != null) {
            if (s.equals("END")) {
                break;
            }
            String[] command = s.split(" ");
            parsedCommands.add(command);
        }
        return parsedCommands;
    }

    private static void calculate(ArrayList<String[]> parsedCommands) {
        Operation currentOperation;
        OperationCreator creator;
        for (String[] command : parsedCommands) {
            switch (command[0]) {
                case "+":
                    creator = new AddCreator();
                    break;
                case "DEFINE":
                    creator = new DefineCreator();
                    break;
                case "/":
                    creator = new DivideCreator();
                    break;
                case "*":
                    creator = new MultiplyCreator();
                    break;
                case "POP":
                    creator = new PopCreator();
                    break;
                case "PRINT":
                    creator = new PrintCreator();
                    break;
                case "PUSH":
                    creator = new PushCreator();
                    break;
                case "SQRT":
                    creator = new SQRTCreator();
                    break;
                case "-":
                    creator = new SubtractCreator();
                    break;
                default:
                    if (command[0].charAt(0) != '#') {
                        System.out.println("Undefined command!");
                    }
                    continue;
            } //Choose concrete creator for operation
            currentOperation = creator.createOperation(context);
            LinkedList<String> commArgs = new LinkedList<>(Arrays.asList(command));
            commArgs.removeFirst(); // remove command name
            try {
                currentOperation.calculate(commArgs);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

}
