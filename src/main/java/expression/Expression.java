/*!! Expression */

/*!
Expression
==============

Expression can be written as the following
[Algebraic Data Type (ADT)](https://en.wikipedia.org/wiki/Algebraic_data_type)

    Expression = Addition(leftHandSide: Expression, rightHandSide: Expression)
               | Subtraction(leftHandSide: Expression, rightHandSide: Expression)
               | Identifier(name: String)
               | Int(value: int)
*/

/*!- Header */
package expression;

/*! `Expression` is the common abstract class for Expressions that can be evaluated using the `Evaluator`. */
abstract public class Expression { }
