package interpreter;

import expression.*;

import java.util.HashMap;
import java.util.Map;

public class Evaluator extends Visitor<Integer> {

    final Expression expression;
    final Map<String, Integer> valuation = new HashMap<>();

    public Evaluator(Expression expression, Map<String, Integer> valuation) {
        this.expression = expression;
        this.valuation.putAll(valuation);
    }

    public int eval() {
        return visit(expression);
    }

    public Integer visitAddition(Addition addition) {
        return visit(addition.leftHandSide) + visit(addition.rightHandSide);
    }

    public Integer visitSubtraction(Subtraction subtraction) {
        return visit(subtraction.leftHandSide) - visit(subtraction.rightHandSide);
    }

    public Integer visitInt(Int integer) {
        return integer.value;
    }

    public Integer visitIdentifier(Identifier identifier) {
        if (valuation.containsKey(identifier.name)) {
            return valuation.get(identifier.name);
        } else {
            throw new InterpreterException("Identifier " + identifier.name + " not found.");
        }
    }
}
