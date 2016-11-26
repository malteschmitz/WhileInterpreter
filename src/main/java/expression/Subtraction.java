/*!! Expression */

/*! # Subtraction */

/*!- Header */
package expression;

/*!
A `Subtraction` consists of a `leftHandSide` and a `rightHandSide` expression, which are supposed to be subtracted.

For example

    new Subtraction(new Identifier("x"), new Int(2))

represents the code

    x - 2
*/
public class Subtraction extends Expression {
    public final Expression leftHandSide;
    public final Expression rightHandSide;

    public Subtraction(Expression leftHandSide, Expression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    /*!- String serialization */
    @Override
    public String toString() {
        return "(" + leftHandSide + " - " + rightHandSide + ")";
    }

    /*!- generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subtraction that = (Subtraction) o;

        if (!leftHandSide.equals(that.leftHandSide)) return false;
        return rightHandSide.equals(that.rightHandSide);

    }
}
