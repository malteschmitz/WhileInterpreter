/*!! Program */

/*!
Program
=======

`Program` can be written as the following
[Algebraic Data Type (ADT)](https://en.wikipedia.org/wiki/Algebraic_data_type)

    Program = Assignment(identifier: Identifier, expression: Expression)
            | Composition(first: Program, second: Program)
            | Loop(condition: Expression, program: Program)
            | Conditional(condition: Expression, thenCase: Program, elseCase: Program)
*/

/*!- Header */
package program;

/*! `Program` is the abstract common class for programs that can be executed using the `Interpreter`. */
abstract public class Program { }
