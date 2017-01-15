package printer;

import interpreter.Visitor;
import program.*;

public class ProgramPrinter extends Visitor<String> {
    private final String value;

    public ProgramPrinter(Program program) {
        value = visit(program);
    }

    public String visitAssignment(Assignment assignment) {
        ExpressionPrinter identifierPrinter = new ExpressionPrinter(assignment.identifier);
        ExpressionPrinter expressionPrinter = new ExpressionPrinter(assignment.expression);
        return identifierPrinter.getValue() + " := " + expressionPrinter.getValue();
    }

    public String visitComposition(Composition composition) {
        return visit(composition.first) + " ; " + visit(composition.second);
    }

    public String visitConditional(Conditional conditional) {
        ExpressionPrinter conditionPrinter = new ExpressionPrinter(conditional.condition);
        return "if (" + conditionPrinter.getValue() + ") then { " + visit(conditional.thenCase) +  " } else { " + visit(conditional.elseCase) + " }";
    }

    public String visitLoop(Loop loop) {
        ExpressionPrinter conditionPrinter = new ExpressionPrinter(loop.condition);
        return "while (" + conditionPrinter.getValue() + ") { " + visit(loop.program) +  " }";
    }

    public String getValue() {
        return value;
    }
}
