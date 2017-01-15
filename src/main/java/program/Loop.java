/*!! Program */

/*!
Loop
====
*/

/*!- Header */
package program;

import expression.Expression;

/*! A `Loop` consists of a `condition` and a `program` with the intended semantics of execution the `program` while
the `condition` evaluates to a non-zero value. */
public class Loop extends Program {
    public final Expression condition;
    public final Program program;

    public Loop(Expression condition, Program program) {
        this.condition = condition;
        this.program = program;
    }

    /*!- generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loop loop = (Loop) o;

        if (!condition.equals(loop.condition)) return false;
        return program.equals(loop.program);

    }
}
