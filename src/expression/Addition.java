package expression;

public class Addition extends Expression {
    public final Expression leftHandSide;
    public final Expression rightHandSide;

    public Addition(Expression leftHandSide, Expression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    @Override
    public String toString() {
        return "(" + leftHandSide + " + " + rightHandSide + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Addition addition = (Addition) o;

        if (!leftHandSide.equals(addition.leftHandSide)) return false;
        return rightHandSide.equals(addition.rightHandSide);

    }

    @Override
    public int hashCode() {
        int result = leftHandSide.hashCode();
        result = 31 * result + rightHandSide.hashCode();
        return result;
    }
}
