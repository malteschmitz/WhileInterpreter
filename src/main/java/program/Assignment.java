/*!! Program */

/*!
Assignment
==========
*/

/*!- Header */

package program;

import expression.Expression;
import expression.Identifier;

/*! An `Assignment` consists of an `identifier` and an `expression` which should be evaluated and the result stored
in the variable named by the identifier.

For example

    new Assignment(new Identifier("x"), new Number(5))

represents the code

    x := 5
*/
public class Assignment extends Program {
    public final Identifier identifier;
    public final Expression expression;

    public Assignment(Identifier identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    /*!- String serialization */
    @Override
    public String toString() {
        return identifier + " := " + expression;
    }

    /*!- generated equals method */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assignment that = (Assignment) o;

        if (!identifier.equals(that.identifier)) return false;
        return expression.equals(that.expression);
    }
}
