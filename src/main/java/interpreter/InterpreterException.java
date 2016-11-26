/*!! Interpreter */

/*!
InterpreterException
====================
*/

/*!- Header */
package interpreter;

/*! The `InterpreterException` is raised if anything goes wrong during the evaluation of an `Expression` or
running a `Program`. */

public class InterpreterException extends RuntimeException {
    public InterpreterException(String error) {
        super(error);
    }
}
