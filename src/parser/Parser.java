package parser;

import expression.*;
import expression.Number;
import program.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {

    private int position = 0;
    private String input;

    public Parser(String input) {
        this.input = input;
    }

    public Program parse() {
        Optional<Program> programOpt = program();
        Program program = programOpt.orElseThrow(() -> new SyntaxException("Program", position));
        whitespace();
        if (position < input.length()) {
            throw new SyntaxException("End of input", position);
        }
        return program;
    }

    private Optional<Program> program() {
        List<Program> statements = new ArrayList<>();
        boolean run = true;
        int start = position;
        while (run) {
            Optional<Program> statement = statement();
            statement.ifPresent(stmt -> statements.add(stmt));
            if (statement.isPresent()) {
                start = position;
                run = token(";");
            } else {
                position = start;
                run = false;
            }
        }
        Optional<Program> program = Optional.empty();
        for (Program statement: statements) {
            if (!program.isPresent()) {
                program = Optional.of(statement);
            } else {
                program = program.map(pgm -> new Composition(pgm, statement));
            }
        }
        return program;
    }

    private Optional<Program> statement() {
        int start = position;
        Optional<Program> result = assignment();
        if (!result.isPresent()) {
            position = start;
            result = conditional();
            if (!result.isPresent()) {
                position = start;
                result = loop();
            }
        }
        return result;
    }

    private Optional<Program> loop() {
        if (token("while") && token("(")) {
            Optional<Expression> condition = expression();
            return condition.flatMap(cond -> {
                if (token(")") && token("{")) {
                    Optional<Program> program = program();
                    return program.filter(pgm -> token("}")).map(pgm -> new Loop(cond, pgm));
                }
                return Optional.empty();
            });
        }
        return Optional.empty();
    }

    private Optional<Program> conditional() {
        if (token("if") && token("(")) {
            Optional<Expression> condition = expression();
            condition.flatMap(cond -> {
                if (token(")") && token("then") && token("{")) {
                    Optional<Program> thenCase = program();
                    thenCase.flatMap(thenC -> {
                        if (token("}") && token("else") && token("{")) {
                            Optional<Program> elseCase = program();
                            return elseCase.filter(elseC -> token("}")).map(elseC -> new Conditional(cond, thenC, elseC));
                        }
                        return Optional.empty();
                    });
                }
                return Optional.empty();
            });
        }
        return Optional.empty();
    }

    private Optional<Program> assignment() {
        Optional<Identifier> identifier = identifier();
        return identifier.flatMap(id -> {
            if (token(":=")) {
                Optional<Expression> expression = expression();
                return expression.map(exp -> new Assignment(id, exp));
            }
            return Optional.empty();
        });
    }

    private static class OperatorWithExpression {
        private final Operator operator;
        private final Expression expression;

        private OperatorWithExpression(Operator operator, Expression expression) {
            this.operator = operator;
            this.expression = expression;
        }
    }

    private enum Operator {
        PLUS, MINUS, NONE;

        Expression toOperation(Expression leftHandSide, Expression rightHandSide) {
            if (this == Operator.PLUS) {
                return new Addition(leftHandSide, rightHandSide);
            } else if (this == Operator.MINUS) {
                return new Subtraction(leftHandSide, rightHandSide);
            } else {
                throw new RuntimeException("Operator invalid");
            }
        }
    }

    private Optional<Expression> expression() {
        List<OperatorWithExpression> atoms = new ArrayList<>();
        Operator operator = Operator.PLUS;
        int start = position;
        while (operator != Operator.NONE) {
            Optional<Expression> atom = atom();
            Operator op = operator;
            atom.ifPresent(at -> atoms.add(new OperatorWithExpression(op, at)));
            if (atom.isPresent()) {
                start = position;
                operator = operator();
            } else {
                operator = Operator.NONE;
                position = start;
            }
        }
        Optional<Expression> expression = Optional.empty();
        for (OperatorWithExpression atom: atoms) {
            if (!expression.isPresent()) {
                expression = Optional.of(atom.expression);
            } else {
                expression = expression.map(expr -> atom.operator.toOperation(expr, atom.expression));
            }
        }
        return expression;
    }

    private Operator operator() {
        if (token("+")) {
            return Operator.PLUS;
        } else if (token("-")) {
            return Operator.MINUS;
        } else {
            return Operator.NONE;
        }
    }

    private Optional<Expression> atom() {
        int start = position;
        Optional<Expression> result;
        if (token("(")) {
            Optional<Expression> expression = expression();
            result = expression.filter(exp -> token(")"));
        } else {
            position = start;
            result = number();
            if (!result.isPresent()) {
                position = start;
                result = identifier().map(id -> id);
            }
        }
        return result;
    }

    private boolean isLowerLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private Optional<Expression> number() {
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
            return Optional.of(new Number(Integer.parseInt(input.substring(start, position))));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Identifier> identifier() {
        whitespace();
        int start = position;
        while (position < input.length() && isLowerLetter(input.charAt(position))) {
            position += 1;
        }
        if (position > start) {
            Identifier identifier = new Identifier(input.substring(start, position));
            return Optional.of(identifier);
        }
        return Optional.empty();
    }

    private void whitespace() {
        while(position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position += 1;
        }
    }

    private boolean token(String token) {
        whitespace();
        boolean success = position + token.length() <= input.length() && input.substring(position, position + token.length()).equals(token);
        if (success) {
            position += token.length();
        }
        return success;
    }
}
