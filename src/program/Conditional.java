package program;

import expression.Expression;

public class Conditional extends Program {
    public final Expression condition;
    public final Program thenCase;
    public final Program elseCase;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conditional that = (Conditional) o;

        if (!condition.equals(that.condition)) return false;
        if (!thenCase.equals(that.thenCase)) return false;
        return elseCase.equals(that.elseCase);

    }

    @Override
    public String toString() {
        return "if (" + condition + ") then { " + thenCase +  " } else { " + elseCase + " }";
    }

    @Override
    public int hashCode() {
        int result = condition.hashCode();
        result = 31 * result + thenCase.hashCode();
        result = 31 * result + elseCase.hashCode();
        return result;
    }

    public Conditional(Expression condition, Program thenCase, Program elseCase) {
        this.condition = condition;
        this.thenCase = thenCase;
        this.elseCase = elseCase;
    }
}
