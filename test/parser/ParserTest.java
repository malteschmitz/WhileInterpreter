package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import expression.*;
import parser.Parser.Operator;
import program.*;

import java.util.Optional;

public class ParserTest {
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

    @Test
    public void testParse() {
        Parser parser = new Parser(programCode);
        assertEquals(program, parser.parse());
    }

    @Test
    public void testProgram() {
        Parser parser = new Parser(programCode);
        assertEquals(Optional.of(program), parser.program());
    }

    @Test
    public void testStatementAssignment() {
        Parser parser = new Parser(assignmentCode);
        assertEquals(Optional.of(assignment), parser.statement());
    }

    @Test
    public void testStatementConditional() {
        Parser parser = new Parser(conditionalCode);
        assertEquals(Optional.of(conditional), parser.statement());
    }

    @Test
    public void testStatementLoop() {
        Parser parser = new Parser(loopCode);
        assertEquals(Optional.of(loop), parser.statement());
    }

    @Test
    public void testAssignment() {
        Parser parser = new Parser(assignmentCode);
        assertEquals(Optional.of(assignment), parser.assignment());
    }

    @Test
    public void testConditional() {
        Parser parser = new Parser(conditionalCode);
        assertEquals(Optional.of(conditional), parser.conditional());
    }

    @Test
    public void testLoop() {
        Parser parser = new Parser(loopCode);
        assertEquals(Optional.of(loop), parser.loop());
    }

    final String expressionCode = "a+b - (c - 56) + -47";
    final Expression expression = new Addition(new Subtraction(new Addition(new Identifier("a"), new Identifier("b")), new Subtraction(new Identifier("c"), new Int(56))), new Int(-47));

    @Test
    public void testExpression() {
        Parser parser = new Parser(expressionCode);
        assertEquals(Optional.of(expression), parser.expression());
    }

    @Test
    public void testOperatorMinus() {
        Parser parser = new Parser("-");
        assertEquals(Operator.MINUS, parser.operator());
    }

    @Test
    public void testOperatorPlus() {
        Parser parser = new Parser("+");
        assertEquals(Operator.PLUS, parser.operator());
    }

    @Test
    public void testAtomExpression() {
        Parser parser = new Parser("(" + expressionCode + ")");
        assertEquals(Optional.of(expression), parser.atom());
    }

    @Test
    public void testAtomNumber() {
        Parser parser = new Parser("37658");
        assertEquals(Optional.of(new Int(37658)), parser.atom());
    }

    @Test
    public void testAtomIdentifier() {
        Parser parser = new Parser("egjfd");
        assertEquals(Optional.of(new Identifier("egjfd")), parser.atom());
    }

    @Test
    public void testNumber() {
        Parser parser = new Parser("37658");
        assertEquals(Optional.of(new Int(37658)), parser.integer());
    }

    @Test
    public void testIdentifier() {
        Parser parser = new Parser("egjfd");
        assertEquals(Optional.of(new Identifier("egjfd")), parser.identifier());
    }

    @Test
    public void testWhitespace() {
        Parser parser = new Parser(" \n\t  x");
        parser.whitespace();
        assertEquals('x', parser.input.charAt(parser.position));
    }

    @Test
    public void testToken() {
        Parser parser = new Parser("gehjfwdk");
        assertTrue(parser.token("gehjfwdk"));
    }
}
