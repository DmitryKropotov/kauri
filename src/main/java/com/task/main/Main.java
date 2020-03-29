package com.task.main;

import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Coding coding = new Coding();
        System.out.println(coding.encodeShortValue(new StringBuilder("0-9dod")));
        List<Character> string = new LinkedList();
        List<Character> string2 = new LinkedList();
        List<Character> string3 = new LinkedList();
        for (long i = 0; i<1000000; i++) {
            string.add('0');
            string2.add('1');
            string3.add('2');
        }
        List<Character>[] longValue = new List[1];
        List<Character>[] longValues = new List[2];
        List<Character>[] longValues2 = new List[3];
        longValue[0] = string;
        longValues[0] = string;
        longValues2[0] = string;
        longValues[1] = string2;
        longValues2[1] = string2;
        longValues2[2] = string3;
        System.out.println(coding.encodeLongValue(longValue));
        System.out.println(coding.encodeLongValue(longValues));
        System.out.println(coding.encodeLongValue(longValues2));
        System.out.println(coding.decodeShortValue("00010010"));
        System.out.println(coding.decodeLongValue("0000001101010010"));
    }

}
