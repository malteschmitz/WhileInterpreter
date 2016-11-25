package interpreter;

import program.*;

import java.util.HashMap;
import java.util.Map;

public class Interpreter extends Visitor {
    final Program program;
    final Map<String, Integer> valuation = new HashMap<>();

    public Map<String, Integer> getValuation() {
        Map<String, Integer> result = new HashMap<>();
        result.putAll(valuation);
        return result;
    }

    public Interpreter(Program program) {
        this.program = program;
        visit(program);
    }

    public void visitAssignment(Assignment assignment) {
        Evaluator evaluator = new Evaluator(assignment.expression, valuation);
        valuation.put(assignment.identifier.name, evaluator.eval());
    }

    public void visitComposition(Composition composition) {
        visit(composition.first);
        visit(composition.second);
    }

    public void visitConditional(Conditional conditional) {
        Evaluator evaluator = new Evaluator(conditional.condition, valuation);
        if (evaluator.eval() != 0) {
            visit(conditional.thenCase);
        } else {
            visit(conditional.elseCase);
        }
    }

    public void visitLoop(Loop loop) {
        Evaluator evaluator = new Evaluator(loop.condition, valuation);
        if (evaluator.eval() != 0) {
            visit(new Composition(loop.program, loop));
        }
    }
}
