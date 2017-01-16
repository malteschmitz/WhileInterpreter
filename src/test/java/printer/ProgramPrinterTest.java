package printer;

import expression.Addition;
import expression.Identifier;
import expression.Int;
import expression.Subtraction;
import org.junit.Test;
import program.*;

import static org.junit.Assert.*;

public class ProgramPrinterTest {
    final String loopCode = "while (a) { r := r + b ; a := a - 1 }";
    final Loop loop = new Loop(new Identifier("a"), new Composition(new Assignment(new Identifier("r"), new Addition(new Identifier("r"), new Identifier("b"))), new Assignment(new Identifier("a"), new Subtraction(new Identifier("a"), new Int(1)))));

    final String assignmentCode = "a := 2";
    final Assignment assignment = new Assignment(new Identifier("a"), new Int(2));

    final String compositionCode = assignmentCode + " ; b := bar";
    final Composition composition = new Composition(assignment, new Assignment(new Identifier("b"), new Identifier("bar")));

    final String conditionalCode = "if (foo - bar) then { x := 5 } else { x := x }";
    final Conditional conditional = new Conditional(new Subtraction(new Identifier("foo"), new Identifier("bar")), new Assignment(new Identifier("x"), new Int(5)), new Assignment(new Identifier("x"), new Identifier("x")));

    final String programCode = compositionCode + " ; " + loopCode + " ; " + conditionalCode;
    final Program program = new Composition(new Composition(composition, loop), conditional);

    final ProgramPrinter printer = new ProgramPrinter();

    @Test
    public void testVisitAssignment() {
        assertEquals(assignmentCode, printer.print(assignment));
    }

    @Test
    public void testVisitComposition() {
        assertEquals(compositionCode, printer.print(composition));
    }

    @Test
    public void testVisitConditional() {
        assertEquals(conditionalCode, printer.print(conditional));
    }

    @Test
    public void testVisitLoop() {
        assertEquals(loopCode, printer.print(loop));
    }

    @Test
    public void testVisitProgram() {
        assertEquals(programCode, printer.print(program));
    }

}