package com.mangarakov;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class CSVMaker {
    private FileWriter fileWriter;

    public CSVMaker(String fileName) {
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            System.err.println(MessageFormat.format("Error while opening file: {0}", e.getLocalizedMessage()));
        }
    }

    public void makeSortedSCV(HashMap<String, Integer> freqDict, int totalWordNumber) {
        for (Map.Entry<String, Integer> outTuple : MapUtil.sortByValue(freqDict).entrySet()) {
            try {
                NumberFormat formatter = new DecimalFormat("#0.00");
                fileWriter.write(outTuple.getKey() + ";" + outTuple.getValue().toString() + ";"
                        + formatter.format((((double)outTuple.getValue())/totalWordNumber*100)) + "%" + "\n");
            } catch (IOException e) {
                System.err.println(MessageFormat.format("Error while opening file: {0}", e.getLocalizedMessage()));
            }
        }
    }
}
