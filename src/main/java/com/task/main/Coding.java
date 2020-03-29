package com.task.main;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

public class Coding {

    public String encodeShortValue(StringBuilder value) {
       if (value.length() > 127) {
           throw new IllegalArgumentException("length shouldn't exceed length of 127 symbols. Try to use long value format");
       }
       StringBuilder result = new StringBuilder(Integer.toBinaryString(value.length()));
       addZerosToStrBuilder(result);
       return result.toString() + "0";
    }

    public String encodeLongValue(List<Character>[] values) {
        StringBuilder decimalLengthValueRepresentation = new StringBuilder();
        if (values.length == 0) {
            return "0000000100000000";
        } else if (values.length == 1) {
            decimalLengthValueRepresentation.append(Integer.toString(values[0].size(), 2));
        } else {
            BigInteger lengthValue = new BigInteger("0");
            for (int i = 0; i < values.length; i++) {
                Iterator<Character> iteratorChar = values[i].iterator();
                while (iteratorChar.hasNext()) {
                    iteratorChar.next();
                    lengthValue = lengthValue.add(new BigInteger("1"));
                }
            }
            if (lengthValue.equals(((new BigInteger("2")).pow(1008)).subtract(new BigInteger("1")))) {
                throw new IllegalArgumentException("length shouldn't exceed length of 2^1008 - 1");
            }
            decimalLengthValueRepresentation.append(lengthValue.toString(2));
        }

        int log2LengthValue = decimalLengthValueRepresentation.length();
        StringBuilder result = new StringBuilder(Integer.toBinaryString(log2LengthValue/8+(log2LengthValue%8 == 0 ? 0: 1)));
        addZerosToStrBuilder(result);
        result.append("1");
        addZerosToStrBuilder(decimalLengthValueRepresentation);
        decimalLengthValueRepresentation.insert(0, '0');
        result.append(decimalLengthValueRepresentation);
        return result.toString();
    }

    private void addZerosToStrBuilder(StringBuilder inputString) {
        while (inputString.length()%8 != 7) {
            inputString.insert(0, "0");
        }
    }

    public int decodeShortValue(String encodedValue) {
        if (encodedValue.length() != 8 || encodedValue.charAt(7) != '0') {
            throw new IllegalArgumentException("length should be 8 and last number should be 0");
        }
        return Integer.parseInt(encodedValue.substring(0, 7), 2);
    }

    public int decodeLongValue(String encodedValue) {
        if (encodedValue.isEmpty()) {
            throw new IllegalArgumentException("encodedValue shouldn't be empty");
        }
        if (encodedValue.length()%8 != 0) {
            throw new IllegalArgumentException("length should be multiple of 8");
        }
        if (encodedValue.charAt(7) != '1') {
            throw new IllegalArgumentException("eighth number should be 1");
        }
        int amountOfAdditionalOctets = Integer.parseInt(encodedValue.substring(0, 7), 2);
        String binaryResult = encodedValue.substring(8);
        if (amountOfAdditionalOctets != binaryResult.length()/8) {
            throw new IllegalArgumentException("wrong format");
        }
        return Integer.parseInt(binaryResult, 2);
    }
}