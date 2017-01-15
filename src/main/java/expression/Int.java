/*!! Expression */

/*!
Int_(eger)_
===========

In order to avoid confusion with Java's `Integer` auto-boxing class for the primitive `int` this wrapper is called
`Int` instead of `Integer`.
*/

/*!- Header */
package expression;

/*!
An `Int` consists only of its `value`. This class is only needed as a wrapper which allows
us to use an integer as an expression.
*/
public class Int extends Expression {
    public final int value;

    public Int(int value) {
        this.value = value;
    }

    /*!- generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Int integer = (Int) o;

        return value == integer.value;
    }
}
