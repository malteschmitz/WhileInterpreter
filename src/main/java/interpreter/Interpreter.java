/*!! Interpreter */

/*!
Interpreter
===========

The interpreter consists of the `Interpreter` defined in this file that can run a `Program` and the `Evaluator`
that can evaluate an `Expression`.

The interpreter implements the semantics defined by the function `sem: Prog * V -> V`, where `V = Id -> Z` is the set
of all variable valuations to the set `Z` set of integers. `sem` is inductively defined as follows:

    sem(x ":=" e, v) = v.update(x, eval(e, v))
    sem(c1 ";" c2) = sem(c2, sem(c1, v))
    sem("if" "(" e ")" "then" "{" c1 "}" else "{" c2 "}") =
      sem(c1, v)    if eval(e, v) != 0
      sem(c2, v)    else
    sem("while" "(" e ")" "{" c "}", v) =
      sem(c ";" "while" "(" e ")" "{" c "}", v)   if eval(e, v) != 0
      v                                           else

with

- a variable valuation `v`,
- an expression `e`,
- an identifier `x` and
- programs `c`, `c1`, `c2`.

The evaluation function `eval` is described at the
[Evaluator](${basePath}/src/main/java/interpreter/Evaluator.java.html).
*/

/*!- Header */
package interpreter;

import program.*;

import java.util.HashMap;
import java.util.Map;

/*! The `Interpreter` implements the semantic function defined above with the help of the
[Visitor](${basePath}/src/main/java/interpreter/Visitor.java.html). The `Interpreter`
runs the given `Program` in the constructor and can be used as follows on a given `program` of type `Program`.

    Interpreter interpreter = new Interpreter(program);
    System.out.println(interpreter.getValuation());

The semantic function `sem` passes along the variable valuation `v`. In the `Interpreter` the valuation is realized
as global variable `valuation`. Because of the in-order execution this global variable always represents the state
of the `v` passed to the semantic function `sem`. */
public class Interpreter extends Visitor {
    final Program program;
    final Map<String, Integer> valuation = new HashMap<String, Integer>();

    public Map<String, Integer> getValuation() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        result.putAll(valuation);
        return result;
    }

    public Interpreter(Program program) {
        this.program = program;
        visit(program);
    }

    /*!
        sem(x ":=" e, v) = v.update(x, eval(e, v))
    */
    public void visitAssignment(Assignment assignment) {
        Evaluator evaluator = new Evaluator(assignment.expression, valuation);
        valuation.put(assignment.identifier.name, evaluator.eval());
    }

    /*!
        sem(c1 ";" c2) = sem(c2, sem(c1, v))
    */
    public void visitComposition(Composition composition) {
        visit(composition.first);
        visit(composition.second);
    }

    /*!
        sem("if" "(" e ")" "then" "{" c1 "}" else "{" c2 "}") =
          sem(c1, v)    if eval(e, v) != 0
          sem(c2, v)    else
    */
    public void visitConditional(Conditional conditional) {
        Evaluator evaluator = new Evaluator(conditional.condition, valuation);
        if (evaluator.eval() != 0) {
            visit(conditional.thenCase);
        } else {
            visit(conditional.elseCase);
        }
    }

    /*!
        sem("while" "(" e ")" "{" c "}", v) =
          sem(c ";" "while" "(" e ")" "{" c "}", v)   if eval(e, v) != 0
          v                                           else
    */
    public void visitLoop(Loop loop) {
        Evaluator evaluator = new Evaluator(loop.condition, valuation);
        if (evaluator.eval() != 0) {
            visit(new Composition(loop.program, loop));
        }
    }
}
