package program;

import expression.Expression;

public class Loop extends Program {
    public final Expression condition;
    public final Program program;

    public Loop(Expression condition, Program program) {
        this.condition = condition;
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loop loop = (Loop) o;

        if (!condition.equals(loop.condition)) return false;
        return program.equals(loop.program);

    }

    @Override
    public String toString() {
        return "while (" + condition + ") { " + program +  " }";
    }

    @Override
    public int hashCode() {
        int result = condition.hashCode();
        result = 31 * result + program.hashCode();
        return result;
    }
}
