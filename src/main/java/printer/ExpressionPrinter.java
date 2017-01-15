package printer;

import expression.*;
import interpreter.Visitor;

public class ExpressionPrinter extends Visitor<String> {
    private final String value;

    public ExpressionPrinter(Expression expression) {
        value = visit(expression);
    }

    public String visitAddition(Addition addition) {
        return visit(addition.leftHandSide) + " + " + visit(addition.rightHandSide);
    }

    public String visitIdentifier(Identifier identifier) {
        return identifier.name;
    }

    public String visitInt(Int integer) {
        return Integer.toString(integer.value);
    }

    public String visitSubtraction(Subtraction subtraction) {
        return visit(subtraction.leftHandSide) + " - " + visit(subtraction.rightHandSide);
    }

    public String getValue() {
        return value;
    }
}
