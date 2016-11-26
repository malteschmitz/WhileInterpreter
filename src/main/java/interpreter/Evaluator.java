/*!! Interpreter */

/*!
Evaluator
=========

The evaluator implements the semantics defined by the function `eval: Expr * V -> Z`, where `V = Id -> Z` is the set
of all variable valuations to the set `Z` set of integers. `eval` is inductively defined as follows:

    eval(e1 "+" e2, v) = eval(e1, v) + eval(e2, v)
    eval(e1 "-" e2, v) = eval(e1, v) − eval(e2, v)
    eval(x, v) = v(x)
    eval(z, v) = z

with

- expressions `e`, `e1`, `e2`,
- a variable valuation `v`,
- and identifier `x` and
- an integer `z`.
*/

/*- Header */
package interpreter;

import expression.*;

import java.util.HashMap;
import java.util.Map;

/*! The `Evaluator` implements the evaluation function defined above with the help of the
[Visitor](${basePath}/src/main/java/interpreter/Visitor.java.html). The `Evaluator`
takes an `Expression` in the constructor and provides a method `eval()` which evaluates
the given expression and returns the result as an integer. For a given `expression` of type `Expression`
it can be used as follows

    Evaluator evaluator = new Evaluator(expression)
    System.out.println(evaluator.eval());

The evaluation function `eval` takes the variable valuation `v`, which is passed on recursively. As the valuation
is not changed during the evaluation process, it can be stored in a global variable which is not changed. */
public class Evaluator extends Visitor<Integer> {

    final Expression expression;
    final Map<String, Integer> valuation = new HashMap<String, Integer>();

    public Evaluator(Expression expression, Map<String, Integer> valuation) {
        this.expression = expression;
        this.valuation.putAll(valuation);
    }

    public int eval() {
        return visit(expression);
    }

    /*!
        eval(e1 "+" e2, v) = eval(e1, v) + eval(e2, v)
    */
    public Integer visitAddition(Addition addition) {
        return visit(addition.leftHandSide) + visit(addition.rightHandSide);
    }

    /*!
        eval(e1 "-" e2, v) = eval(e1, v) - eval(e2, v)
    */
    public Integer visitSubtraction(Subtraction subtraction) {
        return visit(subtraction.leftHandSide) - visit(subtraction.rightHandSide);
    }

    /*!
        eval(x, v) = v(x)
    */
    public Integer visitInt(Int integer) {
        return integer.value;
    }

    public Integer visitIdentifier(Identifier identifier) {
        /*! Make sure that the identifier actually exists in the valuation and raise an exception otherwise. */
        if (valuation.containsKey(identifier.name)) {
            /*!
                eval(z, v) = z
            */
            return valuation.get(identifier.name);
        } else {
            throw new InterpreterException("Identifier " + identifier.name + " not found.");
        }
    }
}
