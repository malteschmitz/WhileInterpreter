package printer;

import expression.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nils on 15.01.2017.
 */
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
        ExpressionPrinter printer = new ExpressionPrinter(addition);
        assertEquals(additionCode, printer.getValue());
    }

    @Test
    public void testVisitIdentifier() {
        ExpressionPrinter printer = new ExpressionPrinter(identifier);
        assertEquals(identifierCode, printer.getValue());
    }

    @Test
    public void testVisitInt() {
        ExpressionPrinter printer = new ExpressionPrinter(integer);
        assertEquals(integerCode, printer.getValue());
    }

    @Test
    public void testVisitSubtraction() {
        ExpressionPrinter printer = new ExpressionPrinter(subtraction);
        assertEquals(subtractionCode, printer.getValue());
    }

}