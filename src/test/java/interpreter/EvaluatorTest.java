package interpreter;

import expression.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EvaluatorTest {
    @Test
    public void testEval() {
        Map<String, Integer> valuation = new HashMap<String, Integer>();
        valuation.put("x", 7);
        valuation.put("y", 2);
        Expression expression = new Addition(new Identifier("x"), new Subtraction(new Identifier("y"), new Int(-4)));
        Evaluator evaluator = new Evaluator(expression, valuation);
        assertEquals(13, evaluator.getValue());
    }
}
