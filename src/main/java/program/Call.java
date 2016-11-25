package program;

import expression.Expression;
import expression.Identifier;

public class Call extends Program {
    final Identifier name;
    final Expression argument;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Call that = (Call) o;

        if (!name.equals(that.name)) return false;
        return argument.equals(that.argument);

    }

    @Override
    public String toString() {
        return name + "(" + argument + ")";
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + argument.hashCode();
        return result;
    }

    public Call(Identifier name, Expression argument) {
        this.name = name;
        this.argument = argument;
    }
}
