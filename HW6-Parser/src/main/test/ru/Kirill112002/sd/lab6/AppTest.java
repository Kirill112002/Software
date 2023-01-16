package ru.Kirill112002.sd.lab6;

import org.junit.Assert;
import org.junit.Test;
import ru.Kirill112002.sd.lab6.Application;
import ru.Kirill112002.sd.lab6.evaluator.EvaluatorVisitor;
import ru.Kirill112002.sd.lab6.parser.ParserVisitor;
import ru.Kirill112002.sd.lab6.printer.PrinterVisitor;
import ru.Kirill112002.sd.lab6.tokenizer.Token;

import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.junit.Assert;

public class AppTest {

    public void runTest(String expr, String expectedTokens, Long expectedResult, String expectedException){
        try{
            final ParserVisitor parser = new ParserVisitor(expr);
            OutputStream outputStream = new ByteArrayOutputStream();
            final PrinterVisitor printer = new PrinterVisitor(parser, outputStream);
            printer.print();
            final String tokensStr = outputStream.toString().replaceAll("\n", "").replaceAll("\r", "");
            Assert.assertEquals(tokensStr, expectedTokens);
            final EvaluatorVisitor evaluator = new EvaluatorVisitor(parser);
            Long result = evaluator.evaluate();
            Assert.assertEquals(result, expectedResult);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), expectedException);
        }
    }

    @Test
    public void testNull() {
        String data = "";
        String expectedTokens = "";
        Long expectedResult = null;
        String expectedException = "Unexpected token: Token[type=END, value=, position=0].";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //Unexpected token: Token[type=END, value=, position=0].
    }

    @Test
    public void testNumber() {
        String data = "2";
        String expectedTokens = "NUMBER(2)";
        Long expectedResult = 2L;
        String expectedException = "";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //NUMBER(2)
        //2
    }

    @Test
    public void testPlus() {
        String data = "2 + 3";
        String expectedTokens = "NUMBER(2) NUMBER(3) ADD";
        Long expectedResult = 5L;
        String expectedException = "";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //NUMBER(2) NUMBER(3) ADD
        //5
    }

    @Test
    public void testMinus() {
        String data = "2 - 3";
        String expectedTokens = "NUMBER(2) NUMBER(3) SUB";
        Long expectedResult = -1L;
        String expectedException = "";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //NUMBER(2) NUMBER(3) SUB
        //-1
    }

    @Test
    public void testMult() {
        String data = "2 * 3";
        String expectedTokens = "NUMBER(2) NUMBER(3) MUL";
        Long expectedResult = 6L;
        String expectedException = "";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //NUMBER(2) NUMBER(3) MUL
        //6
    }

    @Test
    public void testDiv() {
        String data = "6 / 3";
        String expectedTokens = "NUMBER(6) NUMBER(3) DIV";
        Long expectedResult = 2L;
        String expectedException = "";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //NUMBER(6) NUMBER(3) DIV
        //2
    }

    @Test
    public void testParenthesis() {
        String data = "(6 + 3) * 2";
        String expectedTokens = "NUMBER(6) NUMBER(3) ADD NUMBER(2) MUL";
        Long expectedResult = 18L;
        String expectedException = "";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //NUMBER(6) NUMBER(3) ADD NUMBER(2) MUL
        //18
    }

    @Test
    public void testIncorrectParenthesis1() {
        String data = "(6 + 3 * 2";
        String expectedTokens = "";
        Long expectedResult = null;
        String expectedException = "Unexpected token: Token[type=END, value=, position=10].";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //Unexpected token: Token[type=END, value=, position=10].
    }

    @Test
    public void testIncorrectParenthesis2() {
        String data = "6 + 3) * 2";
        String expectedTokens = "";
        Long expectedResult = null;
        String expectedException = "Unexpected token: Token[type=CLOSING_BRACKET, value=), position=5].";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //Unexpected token: Token[type=CLOSING_BRACKET, value=), position=5].
    }

    @Test
    public void testIncorrect() {
        String data = "6 +";
        String expectedTokens = "";
        Long expectedResult = null;
        String expectedException = "Unexpected token: Token[type=END, value=, position=3].";
        runTest(data, expectedTokens, expectedResult, expectedException);
        //Unexpected token: Token[type=END, value=, position=3].
    }

}