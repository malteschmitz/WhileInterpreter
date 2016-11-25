/*!! Parser */
/*!
# Parser
Dieser [rekursiv absteigende Parser](https://de.wikipedia.org/wiki/Rekursiver_Abstieg) versteht sehr einfache While-Programme, die folgender Grammatik in [EBNF](https://de.wikipedia.org/wiki/Erweiterte_Backus-Naur-Form) genügen:

    Prog = Id ":=" Expr |
           Prog ";" Prog |
          "if" "(" Expr ")" "then" "{" Prog "}" "else" "{" Prog "}" |
          "while" "(" Expr ")" "{" Prog "}"
    Expr = Expr "+" Atom |
           Expr "-" Atom |
           Atom
    Atom = Id | Num | "(" Expr ")"

Dabei kann `Num` direkt zu einer beliebigen ganzen Zahl und `Id` direkt zu einem beliebigen Bezeichner aus den Zeichen
`a` bis `z` abgeleitet werden.

Der Parser nimmt einen String mit dem Quelltext entgegen und erzeugt daraus ein `Program`.
*/

/*!- Header */
package parser;

import expression.*;
import expression.Int;
import program.*;

import java.util.ArrayList;
import java.util.List;

/*!
Die Klasse `Parser` implementiert den Parser. Sie wird verwendet, indem dem Konstruktor der zu parsende Quelltext
übergeben wird. Anschließend kann die Methide `parse` aufgerufen werden, die das geparste `Program` zurückgibt.

    Parser parser = new Parser("a := 1");
    Program program = parser.parse();
*/
public class Parser {

/*!
Die Instanzvariable `input` hält den zu parsenden Quelltext und in `position` steht die aktuelle Position des Parsers.
Alle folgenden Methoden betrachten den Quelltext jeweils ab der aktuellen Position und erhöhen diese, wenn Zeichen
des Quelltextes geparsed wurden.
*/
    int position;
    String input;

    public Parser(String input) {
        this.input = input;
    }

    public Program parse() {
        position = 0;
        Program program = program();
        whitespace();
        if (position < input.length()) {
            throw new SyntaxException("End of input", position);
        }
        return program;
    }

    Program program() {
        Program firstStatement = statement();
        List<Program> moreStatements = new ArrayList<Program>();
        while (test(";")) {
            consume(";");
            Program statement = statement();
            moreStatements.add(statement);
        }
        Program program = firstStatement;
        for (Program statement: moreStatements) {
            program = new Composition(program, statement);
        }
        return program;
    }

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

    Program assignment() {
        Identifier identifier = identifier();
        consume(":=");
        Expression expression = expression();
        return new Assignment(identifier, expression);
    }

    private static class OperatorWithExpression {
        private final Operator operator;
        private final Expression expression;

        OperatorWithExpression(Operator operator, Expression expression) {
            this.operator = operator;
            this.expression = expression;
        }
    }

    private enum Operator { PLUS, MINUS }

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

    private Operator operator() {
        whitespace();
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

    Expression expression() {
        Expression firstAtom = atom();
        List<OperatorWithExpression> moreAtoms = new ArrayList<OperatorWithExpression>();
        while(testOperator()) {
            Operator operator = operator();
            Expression expression = atom();
            moreAtoms.add(new OperatorWithExpression(operator, expression));
        }
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

    Expression atom() {
        int start = position;
        Expression result;
        try {
            consume("(");
            result = expression();
            consume(")");
        } catch (SyntaxException se) {
            position = start;
            try {
                result = integer();
            } catch (SyntaxException se2) {
                result = identifier();
            }
        }
        return result;
    }

    private boolean isLowerLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    Expression integer() {
        whitespace();
        int start = position;
        boolean minus = position < input.length() && input.charAt(position) == '-';
        if (minus) {
            position += 1;
        }
        boolean digitsFound = false;
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position += 1;
            digitsFound = true;
        }
        if (digitsFound) {
            return new Int(Integer.parseInt(input.substring(start, position)));
        } else {
            throw new SyntaxException("Integer", position);
        }
    }

    Identifier identifier() {
        whitespace();
        int start = position;
        while (position < input.length() && isLowerLetter(input.charAt(position))) {
            position += 1;
        }
        if (position > start) {
            return new Identifier(input.substring(start, position));
        } else {
            throw new SyntaxException("Identifier", position);
        }
    }

    private void whitespace() {
        while(position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position += 1;
        }
    }

    private void consume(String token) {
        whitespace();
        if (position + token.length() <= input.length() && input.substring(position, position + token.length()).equals(token)) {
            position += token.length();
        } else {
            throw new SyntaxException(token, position);
        }
    }

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
}
