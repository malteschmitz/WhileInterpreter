/*!! Parser */

/*!
Parser
======

In order to parse simple while programs we use a
[Recursive descent parser](https://en.wikipedia.org/wiki/Recursive_descent_parser). The syntax of our while programs
are defined by the following grammar in
[Extended Backus-Naur Form (EBNF)](https://en.wikipedia.org/wiki/Extended_Backus%E2%80%93Naur_form):

    Prog = Id ":=" Expr |
           Prog ";" Prog |
          "if" "(" Expr ")" "then" "{" Prog "}" "else" "{" Prog "}" |
          "while" "(" Expr ")" "{" Prog "}"
    Expr = Expr "+" Atom |
           Expr "-" Atom |
           Atom
    Atom = Id | Num | "(" Expr ")"

The non-terminal `Num` can be derived into an arbitrary integer. `Id` can be derived into an arbitrary identifier
consisting of the lower case characters from `a` to `z`.

Our parser takes the source code as argument and returns a `Program` object.
*/

/*!- Header */
package parser;

import expression.*;
import expression.Int;
import program.*;

import java.util.ArrayList;
import java.util.List;

/*!
`Parser` provides a constructor which takes the source code as argument. The created object provides the method
`parse` which returns the parsed `Program` object.

    Parser parser = new Parser("a := 1");
    Program program = parser.parse();
*/
public class Parser {

    /*!
    The instance variable `input` contains the source code that should be parsed and `position` contains the current
    position of the parser in the `input` string. The following parsing methods each consider the characters of the
    `input` starting at `position`, e.g. `input.charAt(position)`. After consuming characters of the input the methods
    increment the `position`.
    */
    int position;
    final String input;

    public Parser(String input) {
        this.input = input;
    }

    /*!
    The Basics
    ----------
    */

    /*! We start with defining a helper function that consumes whitespaces, by incrementing the `position` until
    the current character is not a whitespace.

    Such a function is necessary, because we do the tokenization on the fly during the parsing. In more complex
    projects the [tokenization](https://en.wikipedia.org/wiki/Lexical_analysis#Tokenization) would be an extra
    pre-processing step which handles the whitespace removal and creates a stream of tokens out of the input string.*/
    private void whitespace() {
        while(position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position += 1;
        }
    }

    /*! Our parsing functions always want to parse something at the current position in the input and raise an
    exception if not possible. In order to implement the rules containing _or_ we need either look-ahead or back
    tracking in order to decide which branch to take. In those cases we catch the exceptions raised by the called
    sub-parsers.

    The `consume` method consumes the given string by incrementing the `position`. It raises a `SyntaxException`
    if the given string is not the next token in the `input` at the current `position`.*/
    private void consume(String token) {
        whitespace();
        if (position + token.length() <= input.length() && input.substring(position, position + token.length()).equals(token)) {
            position += token.length();
        } else {
            throw new SyntaxException(token, position);
        }
    }

    /*! In some situations we want to perform a look-ahead: We want to test if the next token is the given one or not.
    The `test` function calls the `consume` function defined above and returns if it raised an exception or not. */
    private boolean test(String token) {
        int start = position;
        boolean success;
        try {
            consume(token);
            success = true;
        } catch (SyntaxException se) {
            success = false;
        }
        position = start;
        return success;
    }

    /*!
    Expression
    ----------

    Using the basic mechanisms defined above we can implement parsing functions for expressions as defined in our
    grammar:

        Expr = Expr "+" Atom |
               Expr "-" Atom |
               Atom

    This rule is left recursive: If we want to parse an expression, we try to parse an addition first, which starts with
    an expression, so we parse an expression by trying to parse an addition first, which starts with an expression,
    so we parse an expression by ... In order to implement this rule we need to change it, so that the parsing process
    terminates:

        Expr = Atom { ("+" | "-") Atom }

    In the rule above we replaced the recursion with the repetition indicated by `{` and `}`. Parsing this
    repetitive rule involves two steps: Parsing the sequence of atoms and operators into a list

        List<OperatorWithExpression>

    and translate this list into the real data structure afterwards.

    An operator is either `PLUS` or `MINUS`. */
    private enum Operator { PLUS, MINUS }

    /*! `OperatorWithExpression` stores a pair of an operator and the atom immediately following the operator. */
    private static class OperatorWithExpression {
        private final Operator operator;
        private final Expression expression;

        OperatorWithExpression(Operator operator, Expression expression) {
            this.operator = operator;
            this.expression = expression;
        }
    }

    /*! Using this data structure we now can define the expression parser: */
    Expression expression() {
        /*! Parse the first atom */
        Expression firstAtom = atom();
        List<OperatorWithExpression> moreAtoms = new ArrayList<OperatorWithExpression>();
        /*! Parse more operators and atoms while the helper function `testOperator()` indicates that the `operator()` parser
        will succeed (without raising an expression). */
        while(testOperator()) {
            Operator operator = operator();
            Expression expression = atom();
            moreAtoms.add(new OperatorWithExpression(operator, expression));
        }
        /*! Translate the sequence of operator and atoms into the inductive `Addition` and `Subtraction` data
        structure. We start with the `firstAtom` and replace the `expression` for every element of the list
        with an `Addition` or `Subtraction` combining the old `expression` and the current list element. */
        Expression expression = firstAtom;
        for (OperatorWithExpression atom: moreAtoms) {
            switch (atom.operator) {
                case PLUS:
                    expression = new Addition(expression, atom.expression);
                    break;
                case MINUS:
                    expression = new Subtraction(expression, atom.expression);
                    break;
            }
        }
        return expression;
    }

    /*! The `expression` parser above uses the `operator` parser defined below. */

    private Operator operator() {
        whitespace();
        /*! Only check the character at the current position in the input if the current
        position is a valid position in the input (and not after the end of the input).*/
        char next = (char) 0;
        if (position < input.length()) {
            next = input.charAt(position);
            position += 1;
        }
        if (next == '+') {
            return Operator.PLUS;
        } else if (next == '-') {
            return Operator.MINUS;
        } else {
            throw new SyntaxException("Operator", position);
        }
    }

    /*! In the `expression` parser above we used the method `testOperator` defined below which tests
    if the `operator` parser would succeed without throwing a `SyntaxException`. The implementation calls
    the `operator` parser in a `try` block and returns `false` if the exception was catched and `true`
    otherwise. */
    private boolean testOperator() {
        int start = position;
        boolean result;
        try {
            operator();
            result = true;
        } catch (SyntaxException se) {
            result = false;
        }
        position = start;
        return result;
    }

    /*! The rule for the non-terminal Atom

        Atom = Id | Num | "(" Expr ")"

    can be directly translated into the following `atom` parser. We start by parsing an identifier.
    If this succeeds we are done. If this parser throws a `SyntaxException` we try the next option
    of the _or_: We parse a numeric literal. If the corresponding  raises a `SyntaxException`, too,
    we continue with the atom in braces. If this still raises a `SyntaxException` we pass on this exception
    to our caller (by not catching it). In this case we failed parsing an atom. */
    Expression atom() {
        int start = position;
        Expression result;
        try {
            result = identifier();
        } catch (SyntaxException se) {
            /*! Reset the position. The `identifier` parser has failed, but it might have changed the global
            `position` before raising the `SyntaxException` so we need to reset the position before trying
            another parser.*/
            position = start;
            try {
                result = integer();
            } catch (SyntaxException se2) {
                position = start;
                consume("(");
                result = expression();
                consume(")");
            }
        }
        return result;
    }

    /*! An identifier is a sequence of lower case letters. This helper functions checks if the given character
    could be part of an identifier. */
    private boolean isLowerLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    /*! In order to parse an identifier we increment the current `position` while the current character could be part
    of an identifier. */
    Identifier identifier() {
        whitespace();
        int start = position;
        while (position < input.length() && isLowerLetter(input.charAt(position))) {
            position += 1;
        }

        /*! We need to make sure that the identifier is not empty. */
        if (position > start) {
            return new Identifier(input.substring(start, position));
        } else {
            throw new SyntaxException("Identifier", position);
        }
    }

    /*! Parsing an integer follows more or less the same pattern as parsing an identifier (see above).*/
    Expression integer() {
        whitespace();
        int start = position;
        /*! We check for a unary prefix minus first.*/
        boolean minus = position < input.length() && input.charAt(position) == '-';
        if (minus) {
            position += 1;
        }
        /*! Now we check for at least one digit. */
        boolean digitsFound = false;
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position += 1;
            digitsFound = true;
        }
        /*! In the end we relay on `Integer.parseInt` for translating the string that we found into
        a real integer and wrap the returned value in an `Int` to create an element of the `Expression`
        data structure. */
        if (digitsFound) {
            return new Int(Integer.parseInt(input.substring(start, position)));
        } else {
            throw new SyntaxException("Integer", position);
        }
    }

    /*!
    Program
    -------

    Parsing a program is pretty straight forward if we are able to parse token, identifier and expressions
    using the parser functions defined in the last sections. There is only one problem left, that needs to
    be solved first. The rule

        Prog = Id ":=" Expr |
               Prog ";" Prog |
              "if" "(" Expr ")" "then" "{" Prog "}" "else" "{" Prog "}" |
              "while" "(" Expr ")" "{" Prog "}"

    is again recursive in a way that leads to an endless recursion. We basically apply the same rewriting as
    for the Expr rule and end up with

        Prog = Stmt { ";" Stmt }
        Stmt = Id ":=" Expr |
              "if" "(" Expr ")" "then" "{" Prog "}" "else" "{" Prog "}" |
              "while" "(" Expr ")" "{" Prog "}"

    We start with parsing this new Prog non-terminal in the same way as we parse Expr in `expression`.
    In this case there is only one possible operator between the statements, the sequential operator `;`.
    This simplifies the situation as we do not need to store the operator. Apart from that simplification
    we apply the same idea with its two steps: 1) parsing the sequence and 2) creating the `Program` data structure from the
    list. */

    Program program() {
        /*! Parsing the first statement which must be there */
        Program firstStatement = statement();
        /*! Parsing optional following statements seperated with `;` */
        List<Program> moreStatements = new ArrayList<Program>();
        while (test(";")) {
            consume(";");
            Program statement = statement();
            moreStatements.add(statement);
        }
        /*! We use the first statement as initial result */
        Program program = firstStatement;
        /*! and then replace the result with a `Composition` combining the old result and the new statement.*/
        for (Program statement: moreStatements) {
            program = new Composition(program, statement);
        }
        return program;
    }

    /*! Parsing a statement boils down to trying to parse
    - an assignment and if that fails
    - a conditional and if that fails
    - a loop and if that fails
    - fail completely.*/
    Program statement() {
        int start = position;
        Program statement;
        try {
            statement = assignment();
        } catch (SyntaxException se) {
            position = start;
            try {
                statement = conditional();
            } catch (SyntaxException se2) {
                position = start;
                statement = loop();
            }
        }
        return statement;
    }

    /*! Parsing a loop is very straight forward and just follows the rule
    `"while" "(" Expr ")" "{" Prog "}"`.*/
    Program loop() {
        consume("while");
        consume("(");
        Expression condition = expression();
        consume(")");
        consume("{");
        Program program = program();
        consume("}");
        return new Loop(condition, program);
    }

    /*! Parsing a conditional simply follows the rule
    `"if" "(" Expr ")" "then" "{" Prog "}" "else" "{" Prog "}"`.*/
    Program conditional() {
        consume("if");
        consume("(");
        Expression condition = expression();
        consume(")");
        consume("then");
        consume("{");
        Program thenCase = program();
        consume("}");
        consume("else");
        consume("{");
        Program elseCase = program();
        consume("}");
        return new Conditional(condition, thenCase, elseCase);
    }

    /*! Parsing an assignment simply follows the rule `Id ":=" Expr`.*/
    Program assignment() {
        Identifier identifier = identifier();
        consume(":=");
        Expression expression = expression();
        return new Assignment(identifier, expression);
    }

    /*!
    Checking The End
    ----------------

    Everything that remains to be done is checking that we reached the end of the input after we
    are done. As every parser only consumes as much from the input as needed, the `program` parser
    might end in the middle of the input string. In the following public interface method we call
    the `program` parser and check that we have reached the end of the input afterwards.*/

    public Program parse() {
        position = 0;
        Program program = program();
        /*! Whitespace is the only thing allowed after the program.*/
        whitespace();
        if (position < input.length()) {
            throw new SyntaxException("End of input", position);
        }
        return program;
    }
}
