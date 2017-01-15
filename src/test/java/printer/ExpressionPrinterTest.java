package printer;

import expression.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionPrinterTest {
    final String identifierCode = "a";
    final Identifier identifier = new Identifier(identifierCode);

    final String integerCode = "42";
    final Int integer = new Int(42);

    final String additionCode = "a + 42";
    final Addition addition = new Addition(new Identifier("a"), new Int(42));

    final String subtractionCode = "1 - a";
    final Subtraction subtraction = new Subtraction(new Int(1), new Identifier("a"));


    @Test
    public void testVisitAddition() {
        ExpressionPrinter printer = new ExpressionPrinter();
        assertEquals(additionCode, printer.getValue(addition));
    }

    @Test
    public void testVisitIdentifier() {
        ExpressionPrinter printer = new ExpressionPrinter();
        assertEquals(identifierCode, printer.getValue(identifier));
    }

    @Test
    public void testVisitInt() {
        ExpressionPrinter printer = new ExpressionPrinter();
        assertEquals(integerCode, printer.getValue(integer));
    }

    @Test
    public void testVisitSubtraction() {
        ExpressionPrinter printer = new ExpressionPrinter();
        assertEquals(subtractionCode, printer.getValue(subtraction));
    }

}