package parser;

import expression.Addition;
import expression.Identifier;
import expression.Number;
import expression.Subtraction;
import org.junit.Assert;
import org.junit.Test;
import program.Assignment;
import program.Composition;
import program.Loop;
import program.Program;

public class ParserTest {
    @Test
    public void testParse() {
        String program = "a := 2 ; b := 4 ; r := 0 ; while (a) { r := r + b ; a := a - 1 }";
        Parser parser = new Parser(program);
        Program initialization = new Composition(new Composition(new Assignment(new Identifier("a"), new Number(2)), new Assignment(new Identifier("b"), new Number(4))), new Assignment(new Identifier("r"), new Number(0)));
        Program loop = new Loop(new Identifier("a"), new Composition(new Assignment(new Identifier("r"), new Addition(new Identifier("r"), new Identifier("b"))), new Assignment(new Identifier("a"), new Subtraction(new Identifier("a"), new Number(1)))));
        Program expected = new Composition(initialization, loop);
        Program actual = parser.parse();
        Assert.assertEquals(expected, actual);
    }
}
