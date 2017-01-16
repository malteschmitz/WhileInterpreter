import interpreter.Interpreter;
import interpreter.InterpreterException;
import parser.Parser;
import parser.SyntaxException;
import program.Program;
import printer.ProgramPrinter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No file given");
        } else {
            try {
                String code = readFile(args[0]);
                if (args.length == 2) {
                  if ("--print".equals(args[1])) {
                    print(code);
                  } else {
                    System.err.println("Invalid argument " + args[1]);
                  }
                } else if (args.length == 1) {
                  run(code);
                } else {
                  System.err.println("Too many arguments");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readFile(String filename) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filename));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private static void print(String code) {
        try {
            Parser parser = new Parser(code);
            Program program = parser.parse();
            ProgramPrinter printer = new ProgramPrinter();
            System.out.println(printer.print(program));
        } catch (SyntaxException se) {
            System.err.println(se);
        }
    }

    private static void run(String code) {
        try {
            Parser parser = new Parser(code);
            Program program = parser.parse();
            Interpreter interpreter = new Interpreter(program);
            Map<String, Integer> valuation = interpreter.getValuation();
            System.out.println(valuation);
        } catch (SyntaxException se) {
            System.err.println(se);
        } catch (InterpreterException ie) {
            System.err.println(ie);
        }
    }
}
