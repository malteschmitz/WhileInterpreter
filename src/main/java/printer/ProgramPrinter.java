/*!! Printer */

/*!
ProgramPrinter
==============

The `ProgramPrinter` is used for string serialization of a given `Program`.
*/

/*!- Header */
package printer;

import interpreter.Visitor;
import program.*;

/*!
The `ProgramPrinter` implements the string serialization with the help of the
[Visitor](${basePath}/src/main/java/interpreter/Visitor.java.html).
*/
public class ProgramPrinter extends Visitor<String> {
    private final ExpressionPrinter printer = new ExpressionPrinter();

    public String visitAssignment(Assignment assignment) {
        return printer.print(assignment.identifier) + " := " + printer.print(assignment.expression);
    }

    public String visitComposition(Composition composition) {
        return visit(composition.first) + " ; " + visit(composition.second);
    }

    public String visitConditional(Conditional conditional) {
        return "if (" + printer.print(conditional.condition) + ") then { " + visit(conditional.thenCase) +  " } else { " + visit(conditional.elseCase) + " }";
    }

    public String visitLoop(Loop loop) {
        return "while (" + printer.print(loop.condition) + ") { " + visit(loop.program) +  " }";
    }

    /*!
    The `print` function takes a `Program` instance as an argument and returns
    the string serialization of the given program.
    */
    public String print(Program program) {
        return visit(program);
    }
}
