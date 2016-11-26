/*!! Program*/

/*! # Conditional*/

/*!- Header*/
package program;

import expression.Expression;

/*! A `Conditional` consists of the `condition` expression and the two programs `thenCase` and `elseCase` with the
intended semantics of execution the `elseCase` if the `expression` evaluates to 0 and the `thenCase` otherwise. */
public class Conditional extends Program {
    public final Expression condition;
    public final Program thenCase;
    public final Program elseCase;

    public Conditional(Expression condition, Program thenCase, Program elseCase) {
        this.condition = condition;
        this.thenCase = thenCase;
        this.elseCase = elseCase;
    }

    /*!- generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conditional that = (Conditional) o;

        if (!condition.equals(that.condition)) return false;
        if (!thenCase.equals(that.thenCase)) return false;
        return elseCase.equals(that.elseCase);

    }

    /*!- String serialization*/
    @Override
    public String toString() {
        return "if (" + condition + ") then { " + thenCase +  " } else { " + elseCase + " }";
    }
}
