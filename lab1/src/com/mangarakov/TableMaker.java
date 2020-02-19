package com.mangarakov;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class TableMaker {
    private FileReader fileReader;

    public TableMaker(String fileName) {
        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Error while opening file: " + e.getLocalizedMessage());
        }
    }

    private HashMap<String, Integer> freqDict = new HashMap<String, Integer>();
    private int totalCountWord = 0;

    public HashMap<String, Integer> makeDict() {
        totalCountWord = 0;
        try {
            StringBuilder word = new StringBuilder("");
            int c;
            while ((c = fileReader.read()) != -1) {
                if (Character.isLetterOrDigit(c)) {
                    word.append((char) c);
                } else if (!(word.toString().equals(""))){
                    if (freqDict.containsKey(word.toString())) {
                        freqDict.put(word.toString(), freqDict.get(word.toString()) + 1);
                    } else {
                        freqDict.put(word.toString(), 1);
                        word.setLength(0);
                    }
                    totalCountWord++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
        return freqDict;
    }

    public HashMap<String, Integer> getFreqDict() {
        return freqDict;
    }

    public int getTotalCountWord() {
        return totalCountWord;
    }
}
