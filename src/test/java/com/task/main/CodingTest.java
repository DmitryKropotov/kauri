package com.task.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingTest {

    Coding coding = new Coding();

    @Test
    public void testEncodeShortValueNull() {//1
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> coding.encodeShortValue(null));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testEncodeShortValueZeroLength() {//2
        Assertions.assertEquals("00000000", coding.encodeShortValue(new StringBuilder("")));
    }

    @Test
    public void testEncodeShortValueNormalLength() {//3
        Assertions.assertEquals("00001100", coding.encodeShortValue(new StringBuilder("0-9dod")));
    }

    @Test
    public void testEncodeShortValueLongLength() {//4
        StringBuilder testValue = new StringBuilder();
        for (int i = 0; i<128; i++) {
            testValue.append("0");
        }
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.encodeShortValue(testValue));
        Assertions.assertEquals("length shouldn't exceed length of 127 symbols. Try to use long value format", exception.getMessage());
    }

    @Test
    public void testEncodeLongValueNull() {//1
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> coding.encodeLongValue(null));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testEncodeLongValueOneNullList() {//2
        List<Character>[] arrayTestValue = new List[1];
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> coding.encodeLongValue(arrayTestValue));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testEncodeLongValueEmptyArray() {//3
        Assertions.assertEquals("0000001100000000", coding.encodeLongValue(new ArrayList[0]));
    }

    @Test
    public void testEncodeLongValueOneEmptyList() {//4
        List<Character> testValue = new LinkedList();
        List<Character>[] arrayTestValue = new List[1];
        arrayTestValue[0] = testValue;
        Assertions.assertEquals("0000001100000000", coding.encodeLongValue(arrayTestValue));
    }

    @Test
    public void testEncodeLongValueOneNormalList() {//5
        List<Character> testValue = new LinkedList();
        List<Character>[] arrayTestValue = new List[1];
        for (long i = 0; i<100000; i++) {
            testValue.add('0');
        }
        arrayTestValue[0] = testValue;
        Assertions.assertEquals("00000111000000011000011010100000", coding.encodeLongValue(arrayTestValue));
    }


    @Test
    public void testEncodeLongValueTwoNormaLists() {//6
        List<Character> firstPartOfTestValue = new LinkedList();
        List<Character> secondPartOfTestValue = new LinkedList();
        List<Character>[] arrayTestValue = new List[2];
        for (int i = 0; i<100000; i++) {
            firstPartOfTestValue.add('0');
            secondPartOfTestValue.add('1');
        }
        arrayTestValue[0] = firstPartOfTestValue;
        arrayTestValue[1] = secondPartOfTestValue;
        Assertions.assertEquals("00000111000000110000110101000000", coding.encodeLongValue(arrayTestValue));
    }


    //This is a test case for long length for encodeLongValue() method. Array of Lists, consisted of 2^1008 Characters together, is supposed
    //to be transmitted, but I have no idea how to create such array
    /*@Test
    public void testEncodeLongValueLongLength() {
        List<Character> string = new LinkedList();
        BigInteger arrayLength = new BigInteger(Math.pow(2, 988));
        List<Character>[] arrayStrings = new List[arrayLength.value()];//It is supposed to be a very huge array but I need much power to generate it
        //I have no idea, how to do it
        for (int i = 0; i<arrayLength.value(); i++) {
            List<Character> partOfTestValue = new LinkedList<>();
            for (int j = 0; j < Math.pow(2, 20); j++) {
                partOfTestValue.add('0');
            }
            arrayStrings[i] = partOfTestValue;
        }
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i<127; i++) {
            expected.append("11111111");
        }
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.encodeLongValue(arrayStrings));
        Assertions.assertEquals("length shouldn't exceed length of 2^1008 - 1", exception.getMessage());
    }*/


    @Test
    public void testDecodeShortValueNull() {//1
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> coding.decodeShortValue(null));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testDecodeShortValueNormalValue() {//2
        Assertions.assertEquals(9, coding.decodeShortValue("00010010"));
    }

    @Test
    public void testDecodeShortValueWrongLastSymbol() {//3
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.decodeShortValue("00010011"));
        Assertions.assertEquals("length should be 8 and last number should be 0", exception.getMessage());
    }

    @Test
    public void testDecodeShortValueLongInput() {//4
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.decodeShortValue("0001001000"));
        Assertions.assertEquals("length should be 8 and last number should be 0", exception.getMessage());
    }

    @Test
    public void testDecodeShortValueWrongBinaryFormatNumber() {//5
        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> coding.decodeShortValue("xx010010"));
        Assertions.assertEquals("For input string: \"xx01001\"", exception.getMessage());
    }

    @Test
    public void testDecodeLongValueNull() {//1
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> coding.decodeLongValue(null));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    public void testDecodeLongValueEmptyEncodedValue() {//2
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.decodeLongValue(""));
        Assertions.assertEquals("encodedValue shouldn't be empty", exception.getMessage());
    }


    @Test
    public void testDecodeLongValueNormalValue() {//3
        Assertions.assertEquals(82, coding.decodeLongValue("0000001101010010"));
    }

    @Test
    public void testDecodeLongValueNotMultipleOfEight() {//4
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.decodeLongValue("0001"));
        Assertions.assertEquals("length should be multiple of 8", exception.getMessage());
    }

    @Test
    public void testDecodeLongValueWrongEighthNumber() {//5
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.decodeLongValue("00000000"));
        Assertions.assertEquals("eighth number should be 1", exception.getMessage());
    }

    @Test
    public void testDecodeLongValueWrongFormat() {//6
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> coding.decodeLongValue("0000000110011001"));
        Assertions.assertEquals("wrong format", exception.getMessage());
    }

}