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
    /*!- Visit functions */
    public String visitAssignment(Assignment assignment) {
        ExpressionPrinter printer = new ExpressionPrinter();
        return printer.getValue(assignment.identifier) + " := " + printer.getValue(assignment.expression);
    }

    public String visitComposition(Composition composition) {
        return visit(composition.first) + " ; " + visit(composition.second);
    }

    public String visitConditional(Conditional conditional) {
        ExpressionPrinter printer = new ExpressionPrinter();
        return "if (" + printer.getValue(conditional.condition) + ") then { " + visit(conditional.thenCase) +  " } else { " + visit(conditional.elseCase) + " }";
    }

    public String visitLoop(Loop loop) {
        ExpressionPrinter printer = new ExpressionPrinter();
        return "while (" + printer.getValue(loop.condition) + ") { " + visit(loop.program) +  " }";
    }

    /*!
    The `getValue` function takes a `Program` instance as an argument and returns
    the string serialisation of the given program.
    */
    public String getValue(Program program) {
        return visit(program);
    }
}
