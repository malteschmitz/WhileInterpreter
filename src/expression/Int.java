package expression;

public class Int extends Expression {
    public final int value;

    public Int(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Int integer = (Int) o;

        return value == integer.value;

    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int hashCode() {
        return value;
    }
}
