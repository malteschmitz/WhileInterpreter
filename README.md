# Parser and Interpreter for While Programs [![Build Status](https://travis-ci.org/malteschmitz/WhileInterpreter.svg?branch=master)](https://travis-ci.org/malteschmitz/WhileInterpreter)

Exemplary Java implementation of a _recursive decent parser_, an _abstract syntax tree (AST) data structure_ and a _structural recursive interpreter_ over this data structure.

The purpose of this code is to demonstrate the concepts of _syntax and semantics_. For this purpose a little and very artificial but nevertheless Turing-complete programming language was defined.

The example code in the file `mult.whl` multiplies two integers using addition:

```
a := 2;
b := 4;
r := 0;
while (a) {
    r := r + b ;
    a := a - 1
}
```

## Literate Programming Documentation

The code is documented in a literate programming style using [Atlassian Docco](https://bitbucket.org/brainicorn/atlassian-docco)

- **Expression**
  - [Addition.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/expression/Addition.java.html)
  - [Expression.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/expression/Expression.java.html)
  - [Identifier.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/expression/Identifier.java.html)
  - [Int.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/expression/Int.java.html)
  - [Subtraction.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/expression/Subtraction.java.html)
- **Interpreter**
  - [Evaluator.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/interpreter/Evaluator.java.html)
  - [Interpreter.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/interpreter/Interpreter.java.html)
  - [InterpreterException.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/interpreter/InterpreterException.java.html)
  - [Visitor.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/interpreter/Visitor.java.html)
- **Parser**
  - [Parser.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/parser/Parser.java.html)
  - [SyntaxException.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/parser/SyntaxException.java.html)
- **Printer**
  - [ExpressionPrinter.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/printer/ExpressionPrinter.java.html)
  - [ProgramPrinter.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/printer/ProgramPrinter.java.html)
- **Program**
  - [Assignment.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/program/Assignment.java.html)
  - [Composition.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/program/Composition.java.html)
  - [Conditional.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/program/Conditional.java.html)
  - [Loop.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/program/Loop.java.html)
  - [Program.java](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/program/Program.java.html)

Start with the [Parser](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/parser/Parser.java.html) and the [Interpreter](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/interpreter/Interpreter.java.html) where the formal syntax and semantics of the _while prorgrams_ are defined.

## Building

The maven project can be build with

```
mvn compile
```

which compiles the Java code and automatically generates the docco documentation in `target/docco`.

Without maven you can compile the Java code manually as follows:

```
mkdir -p target/classes
javac -d target/classes src/main/java/*.java src/main/java/**/*.java
```

## Running

You can run the example code in `mult.whl` with

```
java -cp target/classes Main mult.whl
```

The expected output is

```
{a=0, b=4, r=8}
```

## Exercise

In order to understand how this little application works, I suggest trying to extend it. For example you could try to implement the addition assignment `+=` statement which assigns a variable to its current value plus the evaluation of the expression on the right hand side. This has to be done in three steps

1. Add a class `AdditionAssignment` in the `program` package with the attribute `identifier` of type `Identifier` storing the name of the variable that will be assigned and the attributed `expression` of type `Expression` storing the right hand side expression.

2. Update the `Parser` by extending the `Stmt` rule

  ```
  Stmt = ... | Id "+=" Expr
  ```

3. Update the `Interpreter` by extending the `sem` function either by rewriting the new addition assignment statement as an assignment and an addition

  ```
  sem(x "+=" e, v) = sem(x ":=" x "+" e, v)
  ```

  or by directly applying the assignment and the addition

  ```
  sem(x "+=" e, v) = v.update(x, v(x) + eval(e, v))
  ```
