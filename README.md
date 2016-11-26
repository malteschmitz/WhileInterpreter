# Parser and Interpreter for While Programs

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

The code is documented in a literate programming style using [Atlassian Docco](https://bitbucket.org/doklovic_atlassian/atlassian-docco)

Have a look a the [Parser](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/parser/Parser.java.html) and the [Interpreter](https://malteschmitz.github.io/WhileInterpreter/docco/vertical/src/main/java/interpreter/Interpreter.java.html) where the formal syntax and semantics are defined, too.

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

You can the example code in `mult.whl` with

```
java -cp target/classes Main mult.whl
```

The expected output is

```
{a=0, b=4, r=8}
```

## Exercise

In order to understand how this little application works, I suggest trying to extend is. For example you could try to implement the `++` operator which increments a variable. This has to be done in three steps

1. Add a class `Increment` in the `program` package with the attribute `identifier` of type `Identifier` storing the name of the variable that will be incremented.

2. Update the `Parser` by extending the `Stmt` rule

  ```
  Stmt = ... | Id "++"
  ```

3. Update the `Interpreter` by extending the `sem` function either by rewriting the increment operator as an assignment

  ```
  sem(x "++", v) = sem(x ":=" x "+" 1, v)
  ```

  or by directly applying the incrementation

  ```
  sem(x "++", v) = v.update(x, v(x) + 1)
  ```