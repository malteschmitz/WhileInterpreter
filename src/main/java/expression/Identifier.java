/*!! Expression */

/*!
Identifier
==========
*/

/*!- Header */
package expression;

/*! An `Identifier` consists only of the `name` of the identifier. This class is only needed as a wrapper which allows
us to use an identifier as an expression. */
public class Identifier extends Expression {
    public final String name;

    public Identifier(String name) {
        this.name = name;
    }

    /*!- generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        return name.equals(that.name);

    }
}
