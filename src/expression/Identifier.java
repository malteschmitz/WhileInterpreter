package expression;

public class Identifier extends Expression {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        return name.equals(that.name);

    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public final String name;

    public Identifier(String name) {
        this.name = name;
    }
}
