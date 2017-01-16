/*!! Printer */

/*!
ExpressionPrinter
=================

The `ExpressionPrinter` is used for string serialization of a given `Expression`.
*/

/*!- Header */
package printer;

import expression.*;
import interpreter.Visitor;

/*!
The `ExpressionPrinter` implements the string serialization with the help of the
[Visitor](${basePath}/src/main/java/interpreter/Visitor.java.html).
*/
public class ExpressionPrinter extends Visitor<String> {
    public String visitAddition(Addition addition) {
        return visit(addition.leftHandSide) + " + " + visit(addition.rightHandSide);
    }

    public String visitIdentifier(Identifier identifier) {
        return identifier.name;
    }

    public String visitInt(Int integer) {
        return Integer.toString(integer.value);
    }

    public String visitSubtraction(Subtraction subtraction) {
        return visit(subtraction.leftHandSide) + " - " + visit(subtraction.rightHandSide);
    }

    /*!
    The `print` function takes an `Expression` instance as an argument and returns
    the string serialization of the given expression.
    */
    public String print(Expression expression) {
        return visit(expression);
    }
}
