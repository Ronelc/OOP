package oop.ex6.main;

import java.io.*;
import java.lang.*;

/**
 * the main class to run the sJavac.
 */
public class Sjavac {

    /**
     * the main method.
     * @param args path of file to read.
     */
    public static void main(String[] args) {
        try {
            if (args.length!=1){
                throw new IOException();
            }
            String pathName=args[0];
            TextProcessor tp = new TextProcessor();
            tp.processTextFile(pathName);
            System.out.println(0);
        } catch (SJavaError e) {
            System.err.println(e.getMessage());
            System.out.println(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(2);
        }

    }
}