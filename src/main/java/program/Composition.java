package program;

public class Composition extends Program {
    public final Program first;
    public final Program second;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composition that = (Composition) o;

        if (!first.equals(that.first)) return false;
        return second.equals(that.second);

    }

    @Override
    public String toString() {
        return first + " ; " + second;
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    public Composition(Program first, Program second) {
        this.first = first;
        this.second = second;
    }
}
