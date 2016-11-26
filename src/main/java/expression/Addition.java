/*!! Expression */

/*!
Addition
========
*/

/*!- Header */
package expression;

/*!
An `Addition` consists of a `leftHandSide` and a `rightHandSide` expression, which are supposed to be added.

For example

    new Addition(new Identifier("x"), new Int(2))

represents the code

    x + 2
*/
public class Addition extends Expression {
    public final Expression leftHandSide;
    public final Expression rightHandSide;

    public Addition(Expression leftHandSide, Expression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    /*!- String serialization */
    @Override
    public String toString() {
        return "(" + leftHandSide + " + " + rightHandSide + ")";
    }

    /*!- Generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Addition addition = (Addition) o;

        if (!leftHandSide.equals(addition.leftHandSide)) return false;
        return rightHandSide.equals(addition.rightHandSide);

    }
}
