package expression;

public class Subtraction extends Expression {
    public final Expression leftHandSide;
    public final Expression rightHandSide;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subtraction that = (Subtraction) o;

        if (!leftHandSide.equals(that.leftHandSide)) return false;
        return rightHandSide.equals(that.rightHandSide);

    }

    @Override
    public String toString() {
        return "(" + leftHandSide + " - " + rightHandSide + ")";
    }

    @Override
    public int hashCode() {
        int result = leftHandSide.hashCode();
        result = 31 * result + rightHandSide.hashCode();
        return result;
    }

    public Subtraction(Expression leftHandSide, Expression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }
}
