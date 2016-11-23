package program;

import expression.Expression;
import expression.Identifier;

public class Assignment extends Program {
    public final Identifier identifier;
    public final Expression expression;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assignment that = (Assignment) o;

        if (!identifier.equals(that.identifier)) return false;
        return expression.equals(that.expression);

    }

    @Override
    public String toString() {
        return identifier + " := " + expression;
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + expression.hashCode();
        return result;
    }

    public Assignment(Identifier identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }
}
