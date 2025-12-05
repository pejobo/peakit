package com.kodewerk.profile;
	
import java.util.*;
import java.io.*;


public class CheckIntegerTestHarness {

    private static final boolean OPIMIZED = true;

    public static void main(String[] args) throws IOException {
        //try {
            waitForUserInput();
            testDataset("dataset1.dat");
            testDataset("dataset2.dat");
            testDataset("dataset3.dat");
        //} catch(Throwable t) {}
    }

    private static void waitForUserInput() {
        System.console().readLine("Press Enter");
        System.out.println("running...");
    }

    public static void testDataset(String dataset) throws IOException {
        DataInputStream rdr = new DataInputStream(new FileInputStream(dataset));
        int truecount = 0;
        ArrayList<String> dataSet = new ArrayList<>();
        String s;
        try {
            while ((s = rdr.readUTF()) != null) {
                dataSet.add(s);
            }
        } catch (EOFException e) {}
        rdr.close();

        String[] array = dataSet.toArray(new String[0]);

        long starttime = System.currentTimeMillis();
        for (String dataPoint : array) {
            if (checkInteger(dataPoint))
                truecount++;
        }
        System.out.println(truecount + " (count); time " + (System.currentTimeMillis() - starttime));
    }

    public static boolean checkInteger(String testInteger) {
        if (OPIMIZED) {
            return checkInteger_OPTIMIZED(testInteger);
        } else {
            return checkInteger_SLOW(testInteger);
        }
    }

    public static boolean checkInteger_OPTIMIZED(String testInteger) {
        var len = testInteger.length();
        if (len < 2 || len > 5) return false; // number x must be 30 >= x <= 39_999 (based on first digit 3 and range 2 - 100_000)
        if (testInteger.charAt(0) != '3') return false; // first digit is 3
        for (int i = 1; i < len; i++) {
            char c = testInteger.charAt(i);
            if (c < '0' || c > '9') return false; // not a number
        }
        return true;
    }

    public static boolean checkInteger_SLOW(String testInteger) {
        try {
            Integer theInteger = Integer.parseInt(testInteger); //fails if not  a number
            return
                    (theInteger.toString() != "") && //not empty
                    (theInteger.intValue() > 10) && //greater than ten
                    ((theInteger.intValue() >= 2) &&
                    (theInteger.intValue() <= 100000)) && //2>=X<=100000
                    (theInteger.toString().charAt(0) == '3'); //first digit is 3
        } catch (NumberFormatException err) {
            return false;
        }
    }
}
