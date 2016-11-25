package interpreter;

import expression.*;

import java.util.HashMap;
import java.util.Map;

public class Evaluator extends ExpressionVisitor<Integer> {

    final Expression expression;
    final Map<String, Integer> valuation = new HashMap<>();

    public Evaluator(Expression expression, Map<String, Integer> valuation) {
        this.expression = expression;
        this.valuation.putAll(valuation);
    }

    public int eval() {
        return visit(expression);
    }

    @Override
    public Integer visitAddition(Addition addition) {
        return visit(addition.leftHandSide) + visit(addition.rightHandSide);
    }

    @Override
    public Integer visitSubtraction(Subtraction subtraction) {
        return visit(subtraction.leftHandSide) - visit(subtraction.rightHandSide);
    }

    @Override
    public Integer visitInt(Int integer) {
        return integer.value;
    }

    @Override
    public Integer visitIdentifier(Identifier identifier) {
        if (valuation.containsKey(identifier.name)) {
            return valuation.get(identifier.name);
        } else {
            throw new InterpreterException("Identifier " + identifier.name + " not found.");
        }
    }
}
