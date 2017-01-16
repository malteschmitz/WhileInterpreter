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

    final ExpressionPrinter printer = new ExpressionPrinter();

    @Test
    public void testVisitAddition() {
        assertEquals(additionCode, printer.print(addition));
    }

    @Test
    public void testVisitIdentifier() {
        assertEquals(identifierCode, printer.print(identifier));
    }

    @Test
    public void testVisitInt() {
        assertEquals(integerCode, printer.print(integer));
    }

    @Test
    public void testVisitSubtraction() {
        assertEquals(subtractionCode, printer.print(subtraction));
    }

}