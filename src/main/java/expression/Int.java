/*!! Expression*/

/*! # Int */

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

    /*!- String serialization */
    @Override
    public String toString() {
        return Integer.toString(value);
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
