package com.mangarakov;


public class Main {

    public static void main(String[] args) {
        CSVMaker bookStat = new CSVMaker(args[1]);
        TableMaker bookAnalyzer = new TableMaker(args[0]);
        bookAnalyzer.makeDict();
        bookStat.makeSortedSCV(bookAnalyzer.getFreqDict(), bookAnalyzer.getTotalCountWord());
    }
}
