/*!! Program */

/*!
Composition
===========
*/

/*!- Header */
package program;

/*! A `Composition` combines two programs (`first` and `second`) with the intended semantics of sequential
composition. */
public class Composition extends Program {
    public final Program first;
    public final Program second;

    public Composition(Program first, Program second) {
        this.first = first;
        this.second = second;
    }

    /*!- String serialization */
    @Override
    public String toString() {
        return first + " ; " + second;
    }

    /*!- generated equals implementation */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Composition that = (Composition) o;

        if (!first.equals(that.first)) return false;
        return second.equals(that.second);
    }
}
