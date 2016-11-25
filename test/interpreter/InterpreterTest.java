package interpreter;

import expression.Addition;
import expression.Identifier;
import expression.Int;
import expression.Subtraction;
import org.junit.Test;
import program.Assignment;
import program.Composition;
import program.Loop;
import program.Program;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InterpreterTest {
    @Test
    public void testSem() {
        Program initialization = new Composition(
                new Composition(
                        new Assignment(new Identifier("a"), new Int(2)),
                        new Assignment(new Identifier("b"), new Int(4))),
                new Assignment(new Identifier("r"), new Int(0)));
        Program body = new Composition(
                new Assignment(new Identifier("r"), new Addition(new Identifier("r"), new Identifier("b"))),
                new Assignment(new Identifier("a"), new Subtraction(new Identifier("a"), new Int(1))));
        Program loop = new Loop(new Identifier("a"), body);
        Program program = new Composition(initialization, loop);
        Interpreter interpreter = new Interpreter(program);
        Map<String, Integer> valuation = interpreter.getValuation();
        assertEquals(8, valuation.get("r").intValue());
        assertEquals(0, valuation.get("a").intValue());
        assertEquals(4, valuation.get("b").intValue());
    }

    @Test
    public void testSemWithValuation() {
        Map<String, Integer> valuation = new HashMap<>();
        valuation.put("a", 2);
        valuation.put("b", 4);
        valuation.put("r", 0);
        Program body = new Composition(
                new Assignment(new Identifier("r"), new Addition(new Identifier("r"), new Identifier("b"))),
                new Assignment(new Identifier("a"), new Subtraction(new Identifier("a"), new Int(1))));
        Program loop = new Loop(new Identifier("a"), body);
        Interpreter interpreter = new Interpreter(loop, valuation);
        valuation = interpreter.getValuation();
        assertEquals(8, valuation.get("r").intValue());
        assertEquals(0, valuation.get("a").intValue());
        assertEquals(4, valuation.get("b").intValue());
    }
}
